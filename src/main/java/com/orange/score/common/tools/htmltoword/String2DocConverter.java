package com.orange.score.common.tools.htmltoword;

import com.orange.score.common.utils.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 将html文件转换成word文件
 *
 * @author 王文路
 * @date 2015-7-23
 */
public class String2DocConverter {

    private String content;    // 输入文件路径，以.html结尾

    private String outputPath;    // 输出文件路径，以.doc结尾

    public String2DocConverter(String content, String outputPath) {
        super();
        StringBuffer buf = new StringBuffer();
        buf.append("<html>\n" + "<head>\n" + "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "</head>\n" + "<body>");
        buf.append(content);
        buf.append("</body></html>");
        this.content = buf.toString();
        this.outputPath = outputPath;
    }

    public static void main(String[] args) throws Exception {
        String2DocConverter string2DocConverter = new String2DocConverter("", "");
        string2DocConverter.writeWordFile();
    }

    public void download(HttpServletResponse response, String fileName) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = new FileInputStream(this.outputPath);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
        FileUtil.deleteFile(this.outputPath);
    }

    /**
     * 读取html文件到word
     * <p>
     * html文件的路径
     *
     * @return
     * @throws Exception
     */
    public boolean writeWordFile() throws Exception {

        InputStream is = null;
        FileOutputStream fos = null;

        File outputFile = new File(this.outputPath);
        // 2 如果目标路径不存在 则新建该路径
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        try {
            is = IOUtils.toInputStream(this.content, StandardCharsets.UTF_8.name());
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            directory.createDocument("WordDocument", is);

            fos = new FileOutputStream(this.outputPath);
            poifs.writeFilesystem(fos);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) fos.close();
            if (is != null) is.close();
        }
        return false;
    }
}
