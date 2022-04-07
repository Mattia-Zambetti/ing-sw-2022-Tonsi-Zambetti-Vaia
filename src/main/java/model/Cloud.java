//Tonsi
package model;

import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;

import java.util.HashSet;
import java.util.Set;

public class Cloud {
    private Set<Student> studentsOnCloud;
    private static int studentsNumOnCloud;

    //Every cloud is created by receiving a Set of students, it launches a MaxNumberException
    //if the studentsFromBag size isn't the same of the students number on the cloud
    public Cloud(Set<Student> studentsFromBag) throws MaxNumberException {
        for (Student s : studentsFromBag) {
            if (s.getColor() == null) {
                throw new MaxNumberException("Wrong parameter refilling cloud operation");
            }
        }
        if(studentsNumOnCloud==studentsFromBag.size()) {
            studentsOnCloud = new HashSet<>(studentsFromBag);
        }else throw new MaxNumberException("Wrong students number in the parameter for the cloud");
    }


    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.studentsNumOnCloud = studentsNumOnCloud;
    }

    public static int getStudentsNumOnCloud() {
        return studentsNumOnCloud;
    }

    //It returns the students on this cloud in a Set and it removes them from it.
    //It throws an exception if the cloud is empty
    public Set<Student> takeStudents() throws MaxNumberException {
        if(studentsOnCloud.size()>=0) {
            Set<Student> tmpstudents = new HashSet<Student>(studentsOnCloud);
            studentsOnCloud.clear();
            return tmpstudents;
        }else throw new MaxNumberException("Cloud already chosen");
    }

    //It refills the studentsOnCloud set of the cloud if it's empty, it throws an exception
    //if the students are null and if their size isn't the same of the number required
    //on the clouds
    public void refillCloud(Set<Student> students) throws MaxNumberException, AlreadyFilledCloudException {
        if(studentsOnCloud.size()==0) {
            for (Student s : students) {
                if (s.getColor() == null) {
                    throw new MaxNumberException("Wrong parameter refilling cloud operation");
                }
            }
            if (students.size() == getStudentsNumOnCloud())
                studentsOnCloud.addAll(students);
            else throw new MaxNumberException("Wrong size of the set to refill the clouds");
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
