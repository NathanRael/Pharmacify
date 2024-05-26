package com.nathan.pharmacy.models;

import com.nathan.pharmacy.controllers.TableObserver;
import com.nathan.pharmacy.views.ViewFactory;
//singleton design pattern
public class Singleton {
    private static Singleton singleton = null;
    private final ViewFactory viewFactory;
    private final TableObserver tableObserver;

    private Singleton(){
        this.tableObserver = new TableObserver();
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

    public TableObserver getTableObserver(){return  tableObserver;}

}
