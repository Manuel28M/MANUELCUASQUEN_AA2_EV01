package sistemainventario.gui;

import SistemaInventario.Producto;
import SistemaInventario.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaProductos extends JFrame {
    private JTable tblProductos;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private ProductoDAO productoDAO;
    private DefaultTableModel modeloTabla;

    public VentanaProductos() {
        initComponents();
        initListeners();
        productoDAO = new ProductoDAO();
        actualizarTabla();
    }

    private void initComponents() {
        tblProductos = new JTable();
        btnAgregar = new JButton("Agregar Producto");
        btnActualizar = new JButton("Actualizar Producto");
        btnEliminar = new JButton("Eliminar Producto");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tblProductos, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAgregar)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnActualizar)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEliminar)))
                    .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tblProductos, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregar)
                        .addComponent(btnActualizar)
                        .addComponent(btnEliminar))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void initListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirFormularioAgregarProducto();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actualizarProductoSeleccionado();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                eliminarProductoSeleccionado();
            }
        });
    }

    private void abrirFormularioAgregarProducto() {
        FormularioAgregarProducto formulario = new FormularioAgregarProducto();
        formulario.setProductoDAO(productoDAO);
        formulario.setVisible(true);
        formulario.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                actualizarTabla();
            }
        });
    }

    private void actualizarTabla() {
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        String[] columnas = {"ID", "Nombre", "Cantidad", "Precio"};
        Object[][] datos = new Object[productos.size()][4];

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            datos[i][0] = producto.getId();
            datos[i][1] = producto.getNombre();
            datos[i][2] = producto.getCantidad();
            datos[i][3] = producto.getPrecio();
        }

        modeloTabla = new DefaultTableModel(datos, columnas);
        tblProductos.setModel(modeloTabla);
    }

    private void actualizarProductoSeleccionado() {
        int filaSeleccionada = tblProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProducto = (int) tblProductos.getValueAt(filaSeleccionada, 0);
            Producto producto = productoDAO.obtenerProductoPorId(idProducto);

            if (producto != null) {
                FormularioActualizarProducto formulario = new FormularioActualizarProducto(producto, productoDAO);
                formulario.setVisible(true);
                formulario.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        actualizarTabla();
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar.");
        }
    }

    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = tblProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProducto = (int) tblProductos.getValueAt(filaSeleccionada, 0);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                productoDAO.eliminarProducto(idProducto);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaProductos().setVisible(true);
            }
        });
    }
}
