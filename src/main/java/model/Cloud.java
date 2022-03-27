//Tonsi
package model;

import java.util.HashSet;
import java.util.Set;

public class Cloud {
    private Set<Student> studentsOnCloud;
    private static int numStudentsOnCloud;

    public Cloud(){
        studentsOnCloud=new HashSet<>();
    }

    public void setNumStudentsOnCloud(int numStudentsOnCloud){
        Cloud.numStudentsOnCloud=numStudentsOnCloud;
    }

    public Set<Student> removeAllStudents(Set<Student> newStudents){
        Set<Student> tmpstudents=new HashSet<Student>();
        tmpstudents.removeAll(studentsOnCloud);
        studentsOnCloud.addAll(newStudents);
        return tmpstudents;
    }
}
