//Tonsi
package model;

import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;

import java.util.HashSet;
import java.util.Set;

public class Cloud {
    private final Set<Student> studentsOnCloud;
    private static int studentsNumOnCloud;

    //Every cloud is created by receiving a Set of students, it launches a MaxNumberException
    //if the studentsFromBag size isn't the same of the students number on the cloud
    public Cloud() {
        studentsOnCloud= new HashSet<>();

    }

    public Cloud(Cloud cloud) {

        this.studentsOnCloud=new HashSet<>();
        for (Student student: cloud.studentsOnCloud) {
            this.studentsOnCloud.add(new Student(student));
        }
    }


    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.studentsNumOnCloud = studentsNumOnCloud;
    }

    public static int getStudentsNumOnCloud() {
        return studentsNumOnCloud;
    }

    //It returns the students on this cloud in a Set and it removes them from it.
    //It throws an exception if the cloud is empty
    public Set<Student> takeStudents() throws WrongCloudNumberException {
        if(studentsOnCloud.size()>0) {
            Set<Student> tmpstudents = new HashSet<>(studentsOnCloud);
            studentsOnCloud.clear();
            return tmpstudents;
        }else throw new WrongCloudNumberException("Cloud has already been chosen");
    }

    //It refills the studentsOnCloud set of the cloud if it's empty, it throws an exception
    //if the students are null and if their size isn't the same of the number required
    //on the clouds
    public void refillCloud(Set<Student> students) throws MaxNumberException, AlreadyFilledCloudException {
        if(studentsOnCloud.size()==0) {
            for (Student s : students) {
                if (s.getColor() == null) {
                    throw new MaxNumberException("Wrong parameter in refilling cloud's operation");
                }
            }
            if (students.size() == getStudentsNumOnCloud())
                studentsOnCloud.addAll(students);
            else throw new MaxNumberException("Wrong size of the set to refill the clouds");
        }else throw new AlreadyFilledCloudException("there's a cloud that has already been filled");
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (Student s: studentsOnCloud) {
            res.append(s.toString()).append("\n");
        }
        return res.toString();
    }


}
