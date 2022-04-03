package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class CloudTest extends TestCase {



    @Test
    void createCloud(){
        Set<Student> students=new HashSet<>();
        for(int i=0; i<1; i++)
            students.add(new Student(1, Color.PINK));
        Cloud cloud=new Cloud(students);
        Cloud.setStudentsNumOnCloud(students.size());
        assertEquals(cloud.toString(), "PINK student\n");
    }
}
