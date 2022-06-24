//Tonsi
package model;

import graphicAssets.CLIgraphicsResources;
import model.exception.AlreadyFilledCloudException;
import model.exception.MaxNumberException;
import model.exception.WrongCloudNumberException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Cloud  implements Serializable {
    private final Set<Student> studentsOnCloud;
    private static int studentsNumOnCloud;
    private final int ID;
    private boolean cloudAlreadyChosen;

    /**Every cloud is created by receiving a Set of students, it launches a MaxNumberException
    *if the studentsFromBag size isn't the same of the students number on the cloud*/
    public Cloud( int ID ) {
        studentsOnCloud= new HashSet<>();
        this.ID = ID;

    }

    /** it creates a copy of a cloud*/
    public Cloud(Cloud cloud) {

        this.studentsOnCloud=new HashSet<>();
        for (Student student: cloud.studentsOnCloud) {
            this.studentsOnCloud.add(new Student(student));
        }
        this.ID = cloud.ID;
        cloudAlreadyChosen = cloud.cloudAlreadyChosen;
    }


    /**It sets the number of players on every cloud in a match*/
    public static void setStudentsNumOnCloud(int studentsNumOnCloud){
        Cloud.studentsNumOnCloud = studentsNumOnCloud;
    }

    /**it returns the number of students on every cloud in this match*/
    public static int getStudentsNumOnCloud() {
        return studentsNumOnCloud;
    }

    /**it allows to set the cloud as a selectable cloud for the players*/
    public void setCloudNotChosen() {
        cloudAlreadyChosen=false;
    }

    /**It returns the students on this cloud in a Set and it removes them from it.
    *It throws an exception if the cloud is empty*/
    public Set<Student> takeStudents() throws WrongCloudNumberException {
        if(!cloudAlreadyChosen) {
            Set<Student> tmpstudents = new HashSet<>(studentsOnCloud);
            studentsOnCloud.clear();
            cloudAlreadyChosen = true;
            return tmpstudents;
        }else throw new WrongCloudNumberException("Cloud has already been chosen");
    }

    /**It refills the studentsOnCloud set of the cloud if it's empty, it throws an exception
    *if the students are null and if their size isn't the same of the number required
    *on the clouds*/
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

    /**it returns the id of the cloud*/
    public int getID() {
        return ID;
    }

    /**It returns a set of every student on the cloud(could be empty)*/
    public Set<Student> getStudentsOnCloud() {
        return studentsOnCloud;
    }

    /** it's the way to see the clouds and their students in the CLI verion of the program*/
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


    /**it returns true if the Object o is a Cloud and if this cloud and the Cloud o have
     *the same id and the same students*/
    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Cloud c))
            return false;
        else
            return ( ( c.ID==this.ID ) && ( c.studentsOnCloud.containsAll(this.studentsOnCloud))
                    && (this.studentsOnCloud.containsAll(c.studentsOnCloud)));
    }



}
