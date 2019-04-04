package com.example.administrator.myapplication.http;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * code-time: 2019/1/23
 * code-author: by kyle
 * coder-wechat:
 * exp:
 **/
public class CustomInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        void log(String message);

        /** A {@link CustomInterceptor.Logger} defaults output appropriate for the current platform. */
        CustomInterceptor.Logger DEFAULT = new CustomInterceptor.Logger() {
            @Override public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public CustomInterceptor() {
        this(Logger.DEFAULT);
    }

    public CustomInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final CustomInterceptor.Logger logger;

    private volatile CustomInterceptor.Level level = CustomInterceptor.Level.NONE;

    /** Change the level at which this interceptor logs. */
    public CustomInterceptor setLevel(CustomInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public CustomInterceptor.Level getLevel() {
        return level;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        CustomInterceptor.Level level = this.level;

        Request request = chain.request();
        if (level == CustomInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == CustomInterceptor.Level.BODY;
        boolean logHeaders = logBody || level == CustomInterceptor.Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");
        if (!logHeaders && hasRequestBody) {
          //  requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        com.orhanobut.logger.Logger.d(requestStartMessage);
       // logger.log(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                  //  logger.log("Content-Type: " + requestBody.contentType());
                //    com.orhanobut.logger.Logger.d("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                 //   logger.log("Content-Length: " + requestBody.contentLength());
                 //   com.orhanobut.logger.Logger.d("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                //    logger.log(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
               // logger.log("--> END " + request.method());
            } else if (bodyHasUnknownEncoding(request.headers())) {
              //  logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

               // logger.log("");
                if (isPlaintext(buffer)) {
                 //   logger.log(buffer.readString(charset));
                 //   logger.log("--> END " + request.method()
                  //          + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                   // logger.log("--> END " + request.method() + " (binary "
                    //        + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
         //   logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
       /* logger.log("<-- "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + " (" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ')');*/

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
            //    logger.log(headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
             //   logger.log("<-- END HTTP");
            } else if (bodyHasUnknownEncoding(response.headers())) {
             //   logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Long gzippedLength = null;
                if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                    gzippedLength = buffer.size();
                    GzipSource gzippedResponseBody = null;
                    try {
                        gzippedResponseBody = new GzipSource(buffer.clone());
                        buffer = new Buffer();
                        buffer.writeAll(gzippedResponseBody);
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }
                    }
                }

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                  //  logger.log("");
                 //   logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                //    logger.log("");
                    //logger.log(buffer.clone().readString(charset));
                   com.orhanobut.logger.Logger.json(buffer.clone().readString(charset));
                }

                if (gzippedLength != null) {
               //     logger.log("<-- END HTTP (" + buffer.size() + "-byte, "
               //             + gzippedLength + "-gzipped-byte body)");
                } else {
                //    logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
}
