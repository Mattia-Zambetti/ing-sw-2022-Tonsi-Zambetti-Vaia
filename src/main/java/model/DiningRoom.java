//Zambo
package model;

import java.util.ArrayList;
import java.util.List;

public class DiningRoom {
    private static final int DINING_ROOM_DIM = 10;
    private Master master;
    private ArrayList<Student> students;
    private final Color roomColor;


    public DiningRoom ( Color colorOfRoom ) {
        this.roomColor = colorOfRoom;
        students = new ArrayList<>(DINING_ROOM_DIM);
    }

    public boolean haveProfessor() {
        if ( this.master != null )
            return true;
        return false;
    }

    public Master removeMaster () throws NoMasterException{
        Master masterCopy;
        if ( this.master != null ) {
            masterCopy = this.master;
            this.master = null;
            return masterCopy;
        }
        throw new NoMasterException("No master in the selected DiningRoom");
    }

    public void insertMaster ( Master newMaster ) {
        // possibile check su master non nullo o cose strane
        this.master = newMaster;
    }

    public int getStudentsNumber () {
        return students.size();
    }

    public void insertStudent ( Student newStudent ) {
        // possibile check su newStudent != null e su numero di studenti giá contenuti lanciando una nuova eccezione
        students.add( newStudent );
    }


}
