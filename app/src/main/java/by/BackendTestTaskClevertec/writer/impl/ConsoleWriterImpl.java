package by.BackendTestTaskClevertec.writer.impl;

import by.BackendTestTaskClevertec.writer.ConsoleWriter;

public class ConsoleWriterImpl implements ConsoleWriter {


    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
