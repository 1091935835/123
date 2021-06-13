import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.YIntervalRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

public class FunctionController {
    private BasicFunctionController ctr = new BasicFunctionController();
    Scanner in = new Scanner(System.in);
    public FunctionController() throws SQLException {

    }
    public void Add() throws SQLException {
        Student stu = new Student();
        stu.Setup();
        ctr.addStu(stu);
    }
    public void Delete() throws SQLException {
        int num;
        System.out.println("输入删除的学号");
        num = in.nextInt();
        Student temp = ctr.SELECTStu(num);
        ctr.deleStu(temp);
        System.out.println("删除成功");
    }
    public void Alter()throws SQLException{
        int num;
        System.out.println("输入要更改的学号");
        num = in.nextInt();
        Student temp = ctr.SELECTStu(num);
        ctr.alter(temp);
        System.out.println("更改成功");
    }
    public void SelectAndShow()throws SQLException{
        int num;
        System.out.println("输入要查询的学号");
        num = in.nextInt();
        System.out.println("查询成功");
        Student temp = ctr.SELECTStu(num);
        System.out.println(temp.getStunum());
        System.out.println(temp.getName());
        System.out.println(temp.getGender());
        System.out.println(temp.getShengfen());
        System.out.println(temp.getSchool());
        System.out.println(temp.getMajor());
        System.out.println(temp.getYear());
    }
    public void DrawrGaphic()throws SQLException {
    ArrayList<String> Years = ctr.getYears();
        Iterator it2 = Years.iterator();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    while (it2.hasNext()) {
        String temp = (String) it2.next();
        dataset.addValue(ctr.getCountByYear(temp), "数据", temp);
    }
    JFreeChart chart = ChartFactory.createBarChart3D("统计图", "年级", "数量", dataset, PlotOrientation.VERTICAL, true, false, false);


    CategoryPlot plot = chart.getCategoryPlot();//设置图的高级属性
    BarRenderer3D renderer = new BarRenderer3D();//3D属性修改
    //对X轴做操作
    CategoryAxis domainAxis = plot.getDomainAxis();
//对Y轴做操作
    ValueAxis rAxis = plot.getRangeAxis();

    /*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
    chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

//设置标题字体
    TextTitle textTitle = chart.getTitle();
    textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
//设置X轴坐标上的文字
    domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
//设置X轴的标题文字
    domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//设置Y轴坐标上的文字
    rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
//设置Y轴的标题文字
    rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
//底部汉字乱码的问题
    chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
    saveAsFile(chart,600,400);
}
    public  void  saveAsFile(JFreeChart chart,
    int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File("graph.png");
            if (!outFile.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                outFile.createNewFile();
            }

            out = new FileOutputStream(outFile);
            // 保存为PNG文件
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);
            // 保存为JPEG文件
            //ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
    public void Useraccount() throws SQLException {
        ctr.CreateAndAuthUser();
    }

}
