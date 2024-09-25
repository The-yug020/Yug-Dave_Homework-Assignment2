package car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarFiles {
    private List<Car> carList;

    public CarFiles() {
        carList = new ArrayList<>();
        loadSampleData();
    }

    private void loadSampleData() {
    	    carList.add(new Car(1, "BMW", "3 Series", 2019, "Black", 35000, "BWM123"));
    	    carList.add(new Car(2, "Audi", "A4", 2020, "White", 36000, "AUD456"));
    	    carList.add(new Car(3, "Mercedes-Benz", "C-Class", 2018, "Silver", 40000, "MB789"));
    	    carList.add(new Car(4, "Volkswagen", "Golf", 2021, "Blue", 29000, "VW321"));
    	    carList.add(new Car(5, "Mazda", "CX-5", 2022, "Red", 32000, "MZ654"));
    	    carList.add(new Car(6, "Subaru", "Outback", 2020, "Green", 33000, "SUB987"));
    	    carList.add(new Car(7, "Chevrolet", "Impala", 2017, "Gold", 24000, "CHE432"));
    	    carList.add(new Car(8, "Ford", "Explorer", 2021, "Gray", 45000, "FD654"));
    	    carList.add(new Car(9, "Toyota", "RAV4", 2022, "Bronze", 36000, "TYO098"));
    	    carList.add(new Car(10, "Honda", "CR-V", 2019, "Dark Blue", 30000, "HND543"));
    	}

    public void saveCarsByMake(String make, String fileName) throws IOException {
        List<Car> filteredCars = carList.stream()
                .filter(car -> car.getMake().equalsIgnoreCase(make))
                .collect(Collectors.toList());
        writeCarsToFile(filteredCars, fileName);
    }

    public void saveCarsByModelAndAge(String model, int age, String fileName) throws IOException {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        List<Car> filteredCars = carList.stream()
                .filter(car -> car.getModel().equalsIgnoreCase(model) && (currentYear - car.getYearOfManufacture() > age))
                .collect(Collectors.toList());
        writeCarsToFile(filteredCars, fileName);
    }

    public void saveCarsByYearAndPrice(int year, double minPrice, String fileName) throws IOException {
        List<Car> filteredCars = carList.stream()
                .filter(car -> car.getYearOfManufacture() == year && car.getPrice() > minPrice)
                .collect(Collectors.toList());
        writeCarsToFile(filteredCars, fileName);
    }

    private void writeCarsToFile(List<Car> cars, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Car car : cars) {
                writer.write(car.toString());
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) {
        CarFiles carFiles = new CarFiles();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select an option:");
        System.out.println("1: Save cars by make");
        System.out.println("2: Save cars by model and age");
        System.out.println("3: Save cars by year and price");

        int choice = scanner.nextInt();
        scanner.nextLine();  

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter car make: ");
                    String make = scanner.nextLine();
                    carFiles.saveCarsByMake(make, "cars_by_"+make+".txt");
                    System.out.println("Cars saved to cars_by_"+make+".txt");
                    break;
                case 2:
                    System.out.print("Enter car model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter age in years: ");
                    int age = scanner.nextInt();
                    carFiles.saveCarsByModelAndAge(model, age, "cars_by_"+model+"_"+age+".txt");
                    System.out.println("Cars saved to cars_by_"+model+"_"+age+".txt");
                    break;
                case 3:
                    System.out.print("Enter manufacture year: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter minimum price: ");
                    double minPrice = scanner.nextDouble();
                    carFiles.saveCarsByYearAndPrice(year, minPrice, "cars_by_"+year+"_"+minPrice+".txt");
                    System.out.println("Cars saved to cars_by_"+year+"_"+minPrice+".txt");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}


