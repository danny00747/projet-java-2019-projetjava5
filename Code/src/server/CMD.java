/**
 * @author Martin Michotte
 * @date 12/11/2019
 */

package server;

import java.io.*;


public class CMD {

    private char escCode = 0x1B;
    private Console console;

    protected CMD() {
        console = System.console();
    }

    /**
     * 
     * @param str {String} -
     */
    protected void print(String str) {
        System.out.print(str);
        //out.write(str);
    }

    /**
     * 
     * @param str {String} -
     */
    protected void println(String str) {
        System.out.println(str);
    }

    /**
     * 
     * @return {String} -
     */
    protected String getUserInput() {
        return console.readLine();
    }

    /**
     * 
     * @param numberOfLines {int} -
     */
    protected void removeLines(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.print(String.format("%c[1A", escCode)); // Move Up 1 line
            System.out.print(String.format("%c[2K", escCode)); // clear the current line
        }
    }

}