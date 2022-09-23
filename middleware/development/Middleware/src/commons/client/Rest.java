package commons.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.messages.Response;

public class Rest {

    private static final Logger logger = LoggerFactory.getLogger(Rest.class);
    private final String http_url;

    public Rest(String http_url) {
        this.http_url = http_url;
    }

    /**
     * Request HTTP
     * <p>
     * [sendHTTPRequestToServer description]
     */
    public Response get() {
        StringBuilder sb = new StringBuilder();
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet request = new HttpGet(http_url);

            String line;

            logger.debug("Executing request " + request.getMethod() + " " + request.getUri());
            try (final CloseableHttpResponse response = httpclient.execute(request)) {
                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        BufferedReader buffer = new BufferedReader(in);

                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }

                        in.close();
                        buffer.close();

                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.debug(e.toString());
        }
        return new Response(code, sb.toString());
    }

    public Response post(String jsonInString) {
        StringBuilder sb = new StringBuilder();
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost request = new HttpPost(http_url);

            String line;

            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(jsonInString);
            request.setEntity(stringEntity);

            logger.debug("Executing request " + request.getMethod() + " " + request.getRequestUri());

            try (final CloseableHttpResponse response = httpclient.execute(request)) {

                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        BufferedReader buffer = new BufferedReader(in);

                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }

                        in.close();
                        buffer.close();

                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }
            }

        } catch (IOException e) {
            logger.debug(e.toString());
        }
        return new Response(code, sb.toString());
    }

    public Response put(String jsonInString) {
        StringBuilder sb = new StringBuilder();
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPut request = new HttpPut(http_url);

            String line;

            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(jsonInString);
            request.setEntity(stringEntity);

            logger.debug("Executing request " + request.getMethod() + " " + request.getRequestUri());

            try (final CloseableHttpResponse response = httpclient.execute(request)) {

                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        BufferedReader buffer = new BufferedReader(in);

                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }

                        in.close();
                        buffer.close();

                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }
            }

        } catch (IOException e) {
            logger.debug(e.toString());
        }
        return new Response(code, sb.toString());
    }

    /**
     * Request HTTP
     * <p>
     * [sendHTTPRequestToServer description]
     */
    public Response delete() {
        StringBuilder sb = new StringBuilder();
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpDelete request = new HttpDelete(http_url);

            String line;

            logger.debug("Executing request " + request.getMethod() + " " + request.getUri());
            try (final CloseableHttpResponse response = httpclient.execute(request)) {
                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        BufferedReader buffer = new BufferedReader(in);

                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }

                        in.close();
                        buffer.close();

                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.debug(e.toString());
        }
        return new Response(code, sb.toString());
    }

    public Response upload(Path path) {
        StringBuilder sb = new StringBuilder();
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost request = new HttpPost(http_url);

            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("uploadfile", path.toFile(), ContentType.APPLICATION_OCTET_STREAM, path.getFileName().toString())
                    .build();

            request.setEntity(reqEntity);

            String line;

            logger.debug("Executing request " + request.getMethod() + " " + request.getRequestUri());

            try (final CloseableHttpResponse response = httpclient.execute(request)) {

                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        BufferedReader buffer = new BufferedReader(in);

                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }

                        in.close();
                        buffer.close();

                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response(code, sb.toString());
    }

    public Response download(Path path) {
        String sb = "";
        int code = 0;

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet request = new HttpGet(http_url);

            logger.debug("Executing request " + request.getMethod() + " " + request.getUri());
            try (final CloseableHttpResponse response = httpclient.execute(request)) {
                logger.debug(response.getCode() + " " + response.getReasonPhrase());
                code = response.getCode();

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                        InputStreamReader in = new InputStreamReader(inStream);
                        OutputStream out = new BufferedOutputStream(Files.newOutputStream(path));

                        entity.writeTo(out);

                        in.close();
                        out.close();

                        sb = "ok";
                    } catch (final IOException ex) {
                        logger.debug(ex.toString());
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.debug(e.toString());
        }
        return new Response(code, sb);
    }

}
