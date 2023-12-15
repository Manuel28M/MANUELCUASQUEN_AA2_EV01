package sistemainventario.gui;

import SistemaInventario.Producto;
import SistemaInventario.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioAgregarProducto extends JFrame {
    private JLabel lblNombre;
    private JLabel lblCantidad;
    private JLabel lblPrecio;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JButton btnAgregar;

    private ProductoDAO productoDAO;

    public FormularioAgregarProducto() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        lblNombre = new JLabel("Nombre:");
        lblCantidad = new JLabel("Cantidad:");
        lblPrecio = new JLabel("Precio:");
        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();
        btnAgregar = new JButton("Agregar Producto");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombre)
                                .addComponent(lblCantidad)
                                .addComponent(lblPrecio))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombre)
                                .addComponent(txtCantidad)
                                .addComponent(txtPrecio)))
                        .addComponent(btnAgregar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre)
                        .addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCantidad)
                        .addComponent(txtCantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPrecio)
                        .addComponent(txtPrecio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnAgregar)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void initListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                agregarProducto();
            }
        });
    }

    private void agregarProducto() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            // Crear objeto Producto con los datos proporcionados
            Producto producto = new Producto(0, nombre, cantidad, precio);

            // Lógica para agregar el producto a la base de datos
            productoDAO.agregarProducto(producto);

            // Cerrar la ventana después de agregar
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para Cantidad y Precio.");
        }
    }

    public void setProductoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioAgregarProducto().setVisible(true);
            }
        });
    }
}
