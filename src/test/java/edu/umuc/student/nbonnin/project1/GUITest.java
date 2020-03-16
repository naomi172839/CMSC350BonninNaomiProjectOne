/*
 * Copyright (c) 2020.
 * Author: Naomi Bonnin
 * School: University of Maryland Global Campus
 * Class: CMSC 350
 * Assignment: Project 1
 * Last Update: 3/16/20, 5:49 PM
 * Description:  The goal of this project was to create a program that correctly evaluates a given infix expression and displays the result to the user.  The project makes use of stacks and uses the provided algorithm.  There are several methods of validating an infix expression included.
 */

package edu.umuc.student.nbonnin.project1;

import org.junit.jupiter.api.Test;

class GUITest {

    //Fails if exception thrown
    @Test
    void createGUI() {
        new GUI();
    }

    //Fails if exception thrown
    @Test
    void createDriver() {
        Driver.main(new String[0]);
    }

}