/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insalyon.dasi.collectif_web;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jindun
 */
public abstract class Action {
    abstract boolean execute(HttpServletRequest request);
}
