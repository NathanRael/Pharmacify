package com.nathan.pharmacy.models;

import com.nathan.pharmacy.views.ViewFactory;
//singleton design pattern
public class Singleton {
    private static Singleton singleton = null;
    private final ViewFactory viewFactory;

    private Singleton(){
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Singleton getInstance(){
        if (singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

}
