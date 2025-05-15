package com.example.CampusCare;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class VolleyMultipartRequest extends Request<NetworkResponse> {

    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;

    public VolleyMultipartRequest(int method, String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // Text parameters
            Map<String, String> params = getParams();
            if (params != null && !params.isEmpty()) {
                textParse(dos, params, getParamsEncoding());
            }

            // Data (file) parameters
            Map<String, DataPart> data = getByteData();
            if (data != null && !data.isEmpty()) {
                dataParse(dos, data);
            }

            // End boundary
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    // Remove 'throws IOException' here
    protected abstract Map<String, String> getParams();

    // Remove 'throws IOException' here
    protected abstract Map<String, DataPart> getByteData();

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(com.android.volley.VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    private void textParse(DataOutputStream dos, Map<String, String> params, String encoding) {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buildTextPart(dos, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dataParse(DataOutputStream dos, Map<String, DataPart> data) throws IOException {
        for (Map.Entry<String, DataPart> entry : data.entrySet()) {
            buildDataPart(dos, entry.getValue(), entry.getKey());
        }
    }

    private void buildTextPart(DataOutputStream dos, String parameterName, String parameterValue) throws IOException {
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.writeBytes(parameterValue + lineEnd);
    }

    private void buildDataPart(DataOutputStream dos, DataPart dataFile, String inputName) throws IOException {
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes(
                "Content-Disposition: form-data; name=\"" +
                        inputName +
                        "\"; filename=\"" +
                        dataFile.getFileName() +
                        "\"" + lineEnd);

        if (dataFile.getType() != null && !dataFile.getType().trim().isEmpty()) {
            dos.writeBytes("Content-Type: " + dataFile.getType() + lineEnd);
        }
        dos.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataFile.getContent());
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bytesRead);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dos.writeBytes(lineEnd);
    }

    public static class DataPart {
        private final String fileName;
        private final byte[] content;
        private final String type;

        public DataPart(String name, byte[] data) {
            fileName = name;
            content = data;
            type = null;
        }

        public DataPart(String name, byte[] data, String mimeType) {
            fileName = name;
            content = data;
            type = mimeType;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }
}
