//Tonsi
package model;

import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;

import java.util.HashSet;
import java.util.Set;

public class Cloud {
    private Set<Student> studentsOnCloud;
    private static int studentsNumOnCloud;

    public Cloud(Set<Student> studentsFromBag){
        studentsOnCloud=new HashSet<>(studentsFromBag);
    }

    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.studentsNumOnCloud = studentsNumOnCloud;
    }

    public static int getStudentsNumOnCloud() {
        return studentsNumOnCloud;
    }

    public Set<Student> takeStudents() throws MaxNumberException {
        if(studentsOnCloud.size()>=0) {
            Set<Student> tmpstudents = new HashSet<Student>(studentsOnCloud);
            studentsOnCloud.clear();
            return tmpstudents;
        }else throw new MaxNumberException("Cloud already chosen");
    }

    public void refillCloud(Set<Student> students) throws MaxNumberException, AlreadyFilledCloudException {
        if(studentsOnCloud.size()==0) {
            for (Student s : students) {
                if (s.getColor() == null) {
                    throw new MaxNumberException("error in the enter parameter");
                }
            }
            if (students.size() == getStudentsNumOnCloud())
                studentsOnCloud.addAll(students);
            else throw new MaxNumberException("error in the enter parameter");
        }else throw new AlreadyFilledCloudException("there's a cloud that is already filled");
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
