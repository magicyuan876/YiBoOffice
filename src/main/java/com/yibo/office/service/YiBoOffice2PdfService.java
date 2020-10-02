package com.yibo.office.service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Service
@Slf4j
public class YiBoOffice2PdfService {

    private String office_path = "D:/officeconvert/officefile/";
    private String pdf_path = "D://";

    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;

    public final static String WPS_WPS = "KWPS.Application";
    public final static String WPS_ET  = "KET.Application";
    public final static String WPS_DPS = "KWPP.Application";

    /**
     * 转换office文件为PDF
     *
     * @param inputFileName
     * @return
     */
    public boolean office2pdf(String inputFileName, String destFileName) {
        String officeUserPath = inputFileName;
        inputFileName = inputFileName.substring(0, inputFileName.lastIndexOf("."));
        String pdfUserPath = String.format(pdf_path + "%s", destFileName + ".pdf");
        int time = convert2PDF(officeUserPath, pdfUserPath);
        boolean result = false;
        if (time == -4) {

            log.info("转化失败，未知错误...");
        } else if (time == -3) {
            result = true;
            log.info("原文件就是PDF文件,无需转化...");
        } else if (time == -2) {
            log.info("转化失败，文件不存在...");
        } else if (time == -1) {
            log.info("转化失败，请重新尝试...");
        } else if (time < -4) {
            log.info("转化失败，请重新尝试...");
        } else {
            result = true;
            log.info("转化成功，用时：  " + time + "s...");
        }
        return result;
    }

    /***
     * 判断需要转化文件的类型（Excel、Word、ppt）
     *
     * @param inputFile
     * @param pdfFile
     */
    private int convert2PDF(String inputFile, String pdfFile) {
        String kind = getFileSufix(inputFile);
        File file = new File(inputFile);
        if (!file.exists()) {
            log.info("文件不存在");
            return -2;
        }
        if (kind.equals("pdf")) {
            log.info("原文件就是PDF文件");
            return -3;
        }
        if (kind.equals("doc") || kind.equals("docx") || kind.equals("txt")) {
            return word2PDF(inputFile, pdfFile);
        } else if (kind.equals("ppt") || kind.equals("pptx")) {
            return ppt2PDF(inputFile, pdfFile);
        } else if (kind.equals("xls") || kind.equals("xlsx")) {
            return Ex2PDF(inputFile, pdfFile);
        } else {
            return -4;
        }
    }

    /***
     * 判断文件类型
     *
     * @param fileName
     * @return
     */
    public String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    public void testWPS() {

        System.out.println("启动 Word...");

        long start = System.currentTimeMillis();

        ActiveXComponent app = null;

        Dispatch doc = null;

        String sfileName = "D://excel.xls";
        String toFileName = "D://2019.pdf";

        try {
            app = new ActiveXComponent("KWPS.Application");  // 基于kwps的方式

            app.setProperty("Visible", new Variant(false));

            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.call(docs, "Open", sfileName).toDispatch();
            System.out.println("打开文档..." + sfileName);

            System.out.println("转换文档到 PDF..." + toFileName);

            File tofile = new File(toFileName);

            if (tofile.exists()) { // 目标pdf存在,则删除,前提未使用

                tofile.delete();

            }

            Dispatch.call(doc, "SaveAs", toFileName, // FileName
                    17);
            long end = System.currentTimeMillis();

            System.out.println("转换完成..用时：" + (end - start) + "ms.");

        } catch (Exception e) {

            System.out.println("========Error:文档转换失败：" + e.getMessage());

        } finally {

            Dispatch.call(doc, "Close", false);

            System.out.println("关闭文档");

            if (app != null)

                app.invoke("Quit", new Variant[]{});

        }

        // 如果没有这句话,winword.exe进程将不会关闭

        ComThread.Release();

    }

    /***
     *
     * Word转PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */

    private int word2PDF(String inputFile, String pdfFile) {
        try {
            // 打开Word应用程序
            ActiveXComponent app = new ActiveXComponent("KWPS.Application");
            log.info("开始转化Word为PDF...");
            long date = new Date().getTime();
            // 获得Word中所有打开的文档，返回documents对象
            Dispatch docs = app.getProperty("Documents").toDispatch();
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            Dispatch doc = Dispatch.call(docs, "Open", inputFile).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) { // 目标pdf存在,则删除,前提未使用
                tofile.delete();
            }
//            Dispatch.call(doc, "SaveAs", tofile.getAbsolutePath(), wdFormatPDF);// word保存为pdf格式宏，值为17


            Dispatch.call(doc, "SaveAs", pdfFile, // FileName
                    17);
            // 关闭文档
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);

            Dispatch.call(doc, "Close", false);
            // 关闭Word应用程序
            app.invoke("Quit", 0);

            ComThread.Release();
            return time;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // TODO: handle exception
            return -1;
        }

    }

    /***
     *
     * Excel转化成PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    private int Ex2PDF(String inputFile, String pdfFile) {
        try {
            ComThread.InitSTA(true);
            ActiveXComponent ax = new ActiveXComponent("KET.Application");
            log.info("开始转化Excel为PDF...");
            long date = new Date().getTime();
            ax.setProperty("Visible", false);
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();

            Dispatch excel = Dispatch
                    .invoke(excels, "Open", Dispatch.Method,
                            new Object[]{inputFile, new Variant(false), new Variant(false)}, new int[9])
                    .toDispatch();
            // 转换格式
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[]{new Variant(0), // PDF格式=0
                    pdfFile, new Variant(xlTypePDF) // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件
                    // (生成的PDF图片糊的一塌糊涂)
            }, new int[1]);

            // 这里放弃使用SaveAs
            /*
             * Dispatch.invoke(excel,"SaveAs",Dispatch.Method,new Object[]{ outFile, new
             * Variant(57), new Variant(false), new Variant(57), new Variant(57), new
             * Variant(false), new Variant(true), new Variant(57), new Variant(true), new
             * Variant(true), new Variant(true) },new int[1]);
             */
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            Dispatch.call(excel, "Close", new Variant(false));

            if (ax != null) {
                ax.invoke("Quit", new Variant[]{});
                ax = null;
            }
            ComThread.Release();
            return time;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // TODO: handle exception
            return -1;
        }
    }

    /***
     * ppt转化成PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    private int ppt2PDF(String inputFile, String pdfFile) {
        log.info("开始转化PPT为PDF...");
        try {
            ComThread.InitSTA(true);
            ActiveXComponent app = new ActiveXComponent("KWPP.Application");
//            app.setProperty("Visible", false);
            long date = new Date().getTime();
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true, // ReadOnly
                    // false, // Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();
            Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{pdfFile, new Variant(ppSaveAsPDF)},
                    new int[1]);
            log.info("PPT");
            Dispatch.call(ppt, "Close");
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            app.invoke("Quit");
            return time;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // TODO: handle exception
            return -1;
        }
    }


    // 删除多余的页，并转换为PDF
    public void interceptPPT(String inputFile, String pdfFile) {
        ActiveXComponent app = null;
        try {
            ComThread.InitSTA(true);
            app = new ActiveXComponent("KWPP.Application");
            ActiveXComponent presentations = app.getPropertyAsComponent("Presentations");
            ActiveXComponent presentation = presentations.invokeGetComponent("Open", new Variant(inputFile),
                    new Variant(false));
            int count = Dispatch.get(presentations, "Count").getInt();
            System.out.println("打开文档数:" + count);
            ActiveXComponent slides = presentation.getPropertyAsComponent("Slides");
            int slidePages = Dispatch.get(slides, "Count").getInt();
            System.out.println("ppt幻灯片总页数:" + slidePages);

            // 总页数的20%取整+1 最多不超过5页
            int target = (int) (slidePages * 0.5) + 1 > 5 ? 5 : (int) (slidePages * 0.5) + 1;
            // 删除指定页数
            while (slidePages > target) {
                // 选中指定页幻灯片
                Dispatch slide = Dispatch.call(presentation, "Slides", slidePages).toDispatch();
                Dispatch.call(slide, "Select");
                Dispatch.call(slide, "Delete");
                slidePages--;
                System.out.println("当前ppt总页数:" + slidePages);
            }
            Dispatch.invoke(presentation, "SaveAs", Dispatch.Method, new Object[]{pdfFile, new Variant(32)},
                    new int[1]);
            Dispatch.call(presentation, "Save");
            Dispatch.call(presentation, "Close");
            presentation = null;
            app.invoke("Quit");
            app = null;
            ComThread.Release();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}