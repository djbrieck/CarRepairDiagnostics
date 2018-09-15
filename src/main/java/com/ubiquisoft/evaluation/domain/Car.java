package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		System.out.println("In the getMissingPartsMap method");


		//Map of missing parts empty.

		Map <PartType, Integer>missingPartsMap  =  new HashMap();


		if( parts == null || parts.isEmpty()){
			//All the parts are missing case
			missingPartsMap.put(PartType.ENGINE, 1);
			missingPartsMap.put(PartType.ELECTRICAL, 1);
			missingPartsMap.put(PartType.FUEL_FILTER, 1);
			missingPartsMap.put(PartType.OIL_FILTER, 1);
			missingPartsMap.put(PartType.TIRE,4);


		}else{
			//Check what we have and build a return as we go.


			Integer engineCount = 0;
			Integer electricalCount = 0;
			Integer fuelFilterCount = 0;
			Integer oilFilterCount = 0;
			Integer tireCount = 0;
			PartType type;

			//Getting and counting the parts
			for (Part part: parts){
               type = part.getType();

               if (type == PartType.ENGINE){
                   engineCount++;
               }else if (type == PartType.ELECTRICAL){
                   electricalCount++;
               }else if (type == PartType.FUEL_FILTER){
                    fuelFilterCount++;
               }else if (type ==PartType.OIL_FILTER){
                    oilFilterCount++;
               }else if (type ==PartType.TIRE){
                    tireCount++;
               }
			}

			//Determine what is missing logic
			if(engineCount == 0 ){
			    missingPartsMap.put(PartType.ENGINE, 1);
            }

            if(electricalCount ==0 ){
                missingPartsMap.put(PartType.ELECTRICAL, 1);
            }

            if(fuelFilterCount ==0 ){
                missingPartsMap.put(PartType.FUEL_FILTER, 1);
            }

            if(oilFilterCount == 0 ){
                missingPartsMap.put(PartType.OIL_FILTER, 1);
            }

            if(tireCount < 4){
                Integer tireBalance = 4 - tireCount;
                missingPartsMap.put(PartType.TIRE, tireBalance);
            }

		}

		return missingPartsMap;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
