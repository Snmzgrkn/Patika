package Week_07.RENTACAR.view;

import Week_07.RENTACAR.business.CarManager;
import Week_07.RENTACAR.business.ModelManager;
import Week_07.RENTACAR.core.ComboItem;
import Week_07.RENTACAR.core.Helper;
import Week_07.RENTACAR.entity.Car;
import Week_07.RENTACAR.entity.Model;

import javax.swing.*;

public class CarView extends Layout {
    private JComboBox cmb_model;
    private JComboBox cmb_color;
    private JTextField fld_km;
    private JTextField fld_plate;
    private JButton btn_car_save;
    private JPanel container;
    private Car car;
    private CarManager carManager;
    private ModelManager modelManager;

    public CarView(Car car){
        this.car = car;
        this.carManager=new CarManager();
        this.modelManager=new ModelManager();
        this.add(container);
        this.guiInitilaze(300,400);

        this.cmb_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));
        for(Model model : this.modelManager.findAll()){
            this.cmb_model.addItem(model.getComboItem());
        }

        if(this.car.getId() !=0){
            ComboItem selectedItem = car.getModel().getComboItem();
            this.cmb_model.getModel().setSelectedItem(selectedItem);
            this.cmb_color.getModel().setSelectedItem(car.getColor());
            this.fld_plate.setText(car.getPlate());
            this.fld_km.setText(Integer.toString(car.getKm()));
        }

        this.btn_car_save.addActionListener(e -> {
            if(Helper.isFieldListEmpty(new JTextField[]{fld_km,fld_plate})){
                Helper.showMessage("fill");
            }else{
                boolean result;
                ComboItem selectedModel = (ComboItem) this.cmb_model.getSelectedItem();
                this.car.setModel_id(selectedModel.getKey());
                this.car.setColor((Car.Color) this.cmb_color.getSelectedItem());
                this.car.setPlate(this.fld_plate.getText());
                this.car.setKm(Integer.parseInt(this.fld_km.getText()));
                if(this.car.getId() != 0){
                    result = this.carManager.update(this.car);
                }else{
                    result = this.carManager.save(this.car);
                }
                if (result){
                    Helper.showMessage("done");
                }else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}
