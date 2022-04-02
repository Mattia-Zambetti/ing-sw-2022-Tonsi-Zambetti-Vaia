//Tonsi
package model;

import java.util.HashSet;
import java.util.Set;

public class Cloud {
    private Set<Student> studentsOnCloud;
    private static int StudentsNumOnCloud;

    public Cloud(Set<Student> studentsFromBag){
        studentsOnCloud=new HashSet<>(studentsFromBag);
    }

    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.StudentsNumOnCloud = studentsNumOnCloud;
    }

    public static int getStudentsNumOnCloud() {
        return StudentsNumOnCloud;
    }

    public Set<Student> takeStudents(){
        Set<Student> tmpstudents=new HashSet<Student>(studentsOnCloud);
        studentsOnCloud.clear();
        return tmpstudents;
    }

    public void refillCloud(Set<Student> students){
        studentsOnCloud.addAll(students);
    }



    @Override
    public String toString(){
        String res = "";
        for (Student s: studentsOnCloud) {
            res+=s.toString()+"\n";
        }
        return res;
    }


}
