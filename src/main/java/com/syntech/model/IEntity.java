/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syntech.model;

import java.io.Serializable;

/**
 *
 * @author rasmi
 */
public interface IEntity extends Serializable {

    Long getId();

    void setId(Long id);
}
