package com.sigif.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class CounterBean {
private int count=0;

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}

//getter setter
public void increment() {
	System.out.println("entrer");
this.count++;
}
}