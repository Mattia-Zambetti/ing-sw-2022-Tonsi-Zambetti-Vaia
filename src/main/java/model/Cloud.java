//Tonsi
package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cloud  implements Serializable {
    private final Set<Student> studentsOnCloud;
    private static int studentsNumOnCloud;
    private final int ID;
    private boolean cloudAlreadyChosen;

    //Every cloud is created by receiving a Set of students, it launches a MaxNumberException
    //if the studentsFromBag size isn't the same of the students number on the cloud
    public Cloud( int ID ) {
        studentsOnCloud= new HashSet<>();
        this.ID = ID;

    }

    public Cloud(Cloud cloud) {

        this.studentsOnCloud=new HashSet<>();
        for (Student student: cloud.studentsOnCloud) {
            this.studentsOnCloud.add(new Student(student));
        }
        this.ID = cloud.ID;
        cloudAlreadyChosen = false;
    }


    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.studentsNumOnCloud = studentsNumOnCloud;
    }

    public static int getStudentsNumOnCloud() {
        return studentsNumOnCloud;
    }

    public void setCloudNotChosen() {
        cloudAlreadyChosen=false;
    }

    //It returns the students on this cloud in a Set and it removes them from it.
    //It throws an exception if the cloud is empty
    public Set<Student> takeStudents() throws WrongCloudNumberException {
        if(!cloudAlreadyChosen) {
            Set<Student> tmpstudents = new HashSet<>(studentsOnCloud);
            studentsOnCloud.clear();
            cloudAlreadyChosen = true;
            return tmpstudents;
        }else throw new WrongCloudNumberException("Cloud has already been chosen");
    }

    //It refills the studentsOnCloud set of the cloud if it's empty, it throws an exception
    //if the students are null and if their size isn't the same of the number required
    //on the clouds
    public void refillCloud(Set<Student> students) throws MaxNumberException, AlreadyFilledCloudException {
        if(studentsOnCloud.size()==0) {
            for (Student s : students) {
                if (s == null) {
                    throw new MaxNumberException("Wrong parameter in refilling cloud's operation");
                }
            }
            studentsOnCloud.addAll(new HashSet<>(students));
        }else throw new AlreadyFilledCloudException("There's a cloud that has already been filled");
    }

    public int getID() {
        return ID;
    }

    public Set<Student> getStudentsOnCloud() {
        return studentsOnCloud;
    }

    @Override
    public String toString(){
        String outputString = "";
        int i = 1;
        outputString = outputString.concat("\n==================================");
        outputString = outputString.concat("\n            CLOUD "+ID+"               \n");
        for ( Student s : studentsOnCloud ) {
            outputString = outputString.concat(CLIgraphicsResources.ColorCLIgraphicsResources.getTextColor(s.getColor()) + " " + s.toString() + CLIgraphicsResources.ColorCLIgraphicsResources.TEXT_COLOR);
            if ( i%2==0 )
                outputString = outputString.concat("\n");
            else {
                outputString = switch (s.getColor()) {
                    case RED -> outputString.concat("      ");
                    case GREEN -> outputString.concat("    ");
                    case YELLOW -> outputString.concat("   ");
                    case BLUE -> outputString.concat("     ");
                    case PINK -> outputString.concat("     ");
                    default -> outputString;
                };
            }
            i++;
        }
        outputString = outputString.concat("\n==================================");
        return outputString;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Cloud c))
            return false;
        else
            return ( ( c.ID==this.ID ) && ( c.studentsOnCloud.equals(this.studentsOnCloud)) );
    }


}
