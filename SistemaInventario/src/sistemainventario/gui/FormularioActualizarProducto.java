package sistemainventario.gui;

import SistemaInventario.Producto;
import SistemaInventario.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioActualizarProducto extends JFrame {
    private JLabel lblNombre;
    private JLabel lblCantidad;
    private JLabel lblPrecio;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JButton btnActualizar;

    private Producto producto;
    private ProductoDAO productoDAO;

    public FormularioActualizarProducto(Producto producto, ProductoDAO productoDAO) {
        this.producto = producto;
        this.productoDAO = productoDAO;
        initComponents();
        initListeners();
        cargarDatosProducto();
    }

    private void initComponents() {
        lblNombre = new JLabel("Nombre:");
        lblCantidad = new JLabel("Cantidad:");
        lblPrecio = new JLabel("Precio:");
        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();
        btnActualizar = new JButton("Actualizar Producto");

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
                        .addComponent(btnActualizar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btnActualizar)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void initListeners() {
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actualizarProducto();
            }
        });
    }

    private void cargarDatosProducto() {
        txtNombre.setText(producto.getNombre());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
    }

    private void actualizarProducto() {
        // Obtener datos actualizados del formulario
        String nuevoNombre = txtNombre.getText();
        int nuevaCantidad = Integer.parseInt(txtCantidad.getText());
        double nuevoPrecio = Double.parseDouble(txtPrecio.getText());

        // Actualizar los datos del producto
        producto.setNombre(nuevoNombre);
        producto.setCantidad(nuevaCantidad);
        producto.setPrecio(nuevoPrecio);

        // Lógica para actualizar el producto en la base de datos
        productoDAO.actualizarProducto(producto);

        // Cerrar la ventana después de actualizar
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioActualizarProducto(new Producto(), new ProductoDAO()).setVisible(true);
            }
        });
    }
}
