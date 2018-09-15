package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		//Validating basic car attributes are present.
		if (car.getMake() == null || car.getMake().isEmpty()){
			System.out.println("Car make must be provided and can't be empty");
			System.exit(2);

		}

		if (car.getModel() == null || car.getModel().isEmpty()){
			System.out.println("Car model information has to be entered and cannot be empty.");
			System.exit(3);
		}

		if(car.getYear() == null || car.getYear().isEmpty()){
			System.out.println("Car year is required and must not be ");
			System.exit(4);
		}

		System.out.printf("Make: %s, model:%s and, year:%s has been provided.\n", car.getMake(), car.getModel(), car.getYear());

		//Determine what is missing.
		Map <PartType, Integer>partsMissingMap;
		partsMissingMap = car.getMissingPartsMap();

		//If we have missing parts then this must stop after printing missing parts.
		if(! partsMissingMap.isEmpty()){
			for (Map.Entry<PartType,Integer> partEntry : partsMissingMap.entrySet()){
				printMissingPart(partEntry.getKey(),partEntry.getValue());
			}

			System.exit(5);
		}

		//Determine the condition of parts
		List<Part> carsParts = car.getParts();

		boolean conditionGood = true;

		for (Part part: carsParts){
			//Not in working condition
			if(! part.isInWorkingCondition())
			{
				printDamagedPart(part.getType(),part.getCondition());
				conditionGood =false;
			}
		}
		if(! conditionGood){
			System.out.println("FAIL: car needs repairs, fix parts noted above.");
			System.exit(6);

		}else{
			System.out.println("No Problems found, all systems PASS.");
		}



	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
