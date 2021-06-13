import java.util.Scanner;

public class Student {
    private int Stunum;
    private String Name;
    private String Major;
    private String Shengfen;
    private String School;
    private String Gender;
    private String year;

    public Student(){

    }
    public Student(int Stunum,String Name,String Major,String Shengfen,String School,String Gender, String year){
        this.Gender=Gender;
        this.Major = Major;
        this.Name = Name;
        this.School = School;
        this.Shengfen = Shengfen;
        this.year = year;
        this.Stunum = Stunum;
    }

    public int getStunum() {
        return Stunum;
    }

    public String getGender() {
        return Gender;
    }

    public String getMajor() {
        return Major;
    }

    public String getName() {
        return Name;
    }

    public String getSchool() {
        return School;
    }

    public String getShengfen() {
        return Shengfen;
    }

    public String getYear() {
        return year;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSchool(String school) {
        School = school;
    }

    public void setShengfen(String shengfen) {
        Shengfen = shengfen;
    }

    public void setStunum(int stunum) {
        Stunum = stunum;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void Setup(){
        System.out.println("请输入个人信息：");
        Scanner in = new Scanner(System.in);
        System.out.println("输入学号");
        setStunum(Integer.parseInt(in.nextLine()));
        System.out.println("输入新名");
        setName(in.nextLine());
        System.out.println("输入性别");
        setGender(in.nextLine());
        System.out.println("输入省份");
        setShengfen(in.nextLine());
        System.out.println("输入学院");
        setSchool(in.nextLine());
        System.out.println("输入专业");
        setMajor(in.nextLine());
        System.out.println("输入年级");
        setYear(in.nextLine());
    }
}
