package com.example.dniapp.util;

import com.example.dniapp.beans.Dni;

import java.util.Comparator;

public class ComparadorDni implements Comparator<Dni> {

    @Override
    public int compare(Dni o1, Dni o2) {
        {
            int num_dev = 0;

                num_dev = o1.getLetra()-o2.getLetra();

            return num_dev;
        }
    }
}
