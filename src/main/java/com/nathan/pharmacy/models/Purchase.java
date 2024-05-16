package com.nathan.pharmacy.models;

import java.util.Date;

/*Purchase( purchaseId, #precId( can be null), #medId,purchaseDate, #patientId,medDesc(can be null) )*/
public class Purchase {
    private int id;
    private String medDesc;
    private Date date;
    private int medId;
    private  int prescId;
    private int patientId;
}
