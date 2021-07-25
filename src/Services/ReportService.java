package Services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Models.Registro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    public CashFlowServices svc = new CashFlowServices();
    public Connection conn;

    //Listas Cobros
    private List<Registro> CobrosSemana1;
    private List<Double> CobrosSemana2;
    private List<Double> CobrosSemana3;
    private List<Double> CobrosSemana4;         //<-----------Updated to Double
    private List<Double> CobrosSemana5;
    private List<Double> CobrosTotal;
    //Listas Pagos
    private List<Registro> PagosSemana1;
    private List<Double> PagosSemana2;
    private List<Double> PagosSemana3;
    private List<Double> PagosSemana4;
    private List<Double> PagosSemana5;
    private List<Double> PagosTotal;
    //Listas Bancos
    private List<Registro> BancosSemana1;
    private List<Double> BancosSemana2;
    private List<Double> BancosSemana3;
    private List<Double> BancosSemana4;
    private List<Double> BancosSemana5;
    private List<Double> BancosTotal;
    //Flujo
    private List<Double> totalFlujosEf;
    private List<Double> totalFlujosCC;
    private List<Double> totalIngresos;
    private List<Double> totalGV;
    private List<Double> totalGO;
    private List<Double> totalGastos;

    private List<Double> totalEgresosC;
    private List<Double> totalEgresosG;
    private List<Double> utilidadesP = new ArrayList<>();

    public void generateReport(String mes) {
        getReportData(mes);
        try {

            //Initialize
            Document documento = new Document();
            FileOutputStream ficheroPDF = new FileOutputStream("Reporte " + mes + ".pdf");
            PdfWriter.getInstance(documento, ficheroPDF);

            //Create
            documento.open();
            Paragraph titulo = new Paragraph("Mes: " + mes + "\n\n",
                    FontFactory.getFont("arial", 20, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(titulo);

            //Tabla Cobros
            Paragraph Cobbros = new Paragraph("Cuentas Por Cobrar" + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(Cobbros);
            PdfPTable tabla = new PdfPTable(7);
            tabla.addCell("Razon");
            tabla.addCell("Semana 1");
            tabla.addCell("Semana 2");
            tabla.addCell("Semana 3");
            tabla.addCell("Semana 4");
            tabla.addCell("Semana 5");
            tabla.addCell(" Final ");
            //Updated
            List<Double> finales = new ArrayList<>();
            for (Double CobrosSemana51 : this.CobrosSemana5) {
                finales.add(Double.parseDouble(String.valueOf(CobrosSemana51)));
            }
            for (int i = 0; i < this.CobrosSemana1.size(); i++) {
                tabla.addCell(this.CobrosSemana1.get(i).getRazon());
                tabla.addCell("$" + String.valueOf(this.CobrosSemana1.get(i).getMonto()));
                tabla.addCell("$" + String.valueOf(this.CobrosSemana2.get(i)));
                tabla.addCell("$" + String.valueOf(this.CobrosSemana3.get(i)));
                tabla.addCell("$" + String.valueOf(this.CobrosSemana4.get(i)));
                tabla.addCell("$" + String.valueOf(this.CobrosSemana5.get(i)));
                tabla.addCell("$" + String.valueOf(finales.get(i)));
            }
            documento.add(tabla);
            PdfPTable CTotales = new PdfPTable(7);
            CTotales.addCell("Total por Cobrar");
            this.CobrosTotal.stream().forEach((CobrosTotal1) -> {
                CTotales.addCell("$" + String.valueOf(CobrosTotal1));
            });
            //Updated
            CTotales.addCell("$" + String.valueOf(this.CobrosTotal.get(this.CobrosTotal.size() - 1)));
            documento.add(CTotales);
            //Fin Tabla Cobros

            //Tabla Pagos
            Paragraph Pagos = new Paragraph("\n Cuentas Por Pagar" + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(Pagos);
            PdfPTable tablaPagos = new PdfPTable(7);
            tabla.addCell("Razon");
            tabla.addCell("Semana 1");
            tabla.addCell("Semana 2");
            tabla.addCell("Semana 3");
            tabla.addCell("Semana 4");
            tabla.addCell("Semana 5");
            tabla.addCell(" Final ");
            List<Double> Pfinales = new ArrayList<>();
            for (Double PSemana51 : this.PagosSemana5) {
                Pfinales.add(Double.parseDouble(String.valueOf(PSemana51)));
            }
            for (int i = 0; i < this.PagosSemana1.size(); i++) {
                tablaPagos.addCell(this.PagosSemana1.get(i).getRazon());
                tablaPagos.addCell("$" + String.valueOf(this.PagosSemana1.get(i).getMonto()));
                tablaPagos.addCell("$" + String.valueOf(this.PagosSemana2.get(i)));
                tablaPagos.addCell("$" + String.valueOf(this.PagosSemana3.get(i)));
                tablaPagos.addCell("$" + String.valueOf(this.PagosSemana4.get(i)));
                tablaPagos.addCell("$" + String.valueOf(this.PagosSemana5.get(i)));
                tablaPagos.addCell("$" + String.valueOf(Pfinales.get(i)));
            }
            documento.add(tablaPagos);
            PdfPTable PTotales = new PdfPTable(7);
            PTotales.addCell("Total por Pagar");
            this.PagosTotal.stream().forEach((PagosTotal1) -> {
                PTotales.addCell("$" + String.valueOf(PagosTotal1));
            });
            PTotales.addCell("$" + String.valueOf(this.PagosTotal.get(this.PagosTotal.size() - 1)));
            documento.add(PTotales);
            //Fin Tabla Pagos

            //Flujo de Efectivo
            //******************************************************************************
            Paragraph Banner = new Paragraph("\n Flujo de Efectivo " + "\n",
                    FontFactory.getFont("arial", 14, Font.BOLD, BaseColor.BLACK) //Title
            );
            Paragraph ingresos = new Paragraph("Ingresos " + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(Banner);
            documento.add(ingresos);
            PdfPTable tablaIngresos = new PdfPTable(7);
            tablaIngresos.addCell("Tipo");
            tablaIngresos.addCell("Semana 1");
            tablaIngresos.addCell("Semana 2");
            tablaIngresos.addCell("Semana 3");
            tablaIngresos.addCell("Semana 4");
            tablaIngresos.addCell("Semana 5");
            tablaIngresos.addCell(" Final ");
            tablaIngresos.addCell("Efectivo");
            List<Double> EgE_finales = new ArrayList<>();
            List<Double> Fcol = new ArrayList<>();
            this.totalFlujosEf.stream().forEach((totalFlujosEf1) -> {
                EgE_finales.add(totalFlujosEf1);
            });
            double efectivo = 0;
            efectivo = EgE_finales.stream().map((EgE_finale) -> EgE_finale).reduce(efectivo, (accumulator, _item) -> accumulator + _item);
            this.totalFlujosEf.add(0.0);
            this.totalFlujosEf.stream().forEach((totalFlujosEf1) -> {
                tablaIngresos.addCell("$" + totalFlujosEf1);
            });
            tablaIngresos.addCell("$" + efectivo);
            documento.add(tablaIngresos);

            //<----Bloque 2
            PdfPTable tablaIngresos2 = new PdfPTable(7);
            tablaIngresos2.addCell("Tarjeta de Credito");
            List<Double> EgCC_finales = new ArrayList<>();
            this.totalFlujosCC.stream().forEach((totalFlujosCC1) -> {
                EgCC_finales.add(totalFlujosCC1);
            });
            double deposito = 0;
            deposito = EgCC_finales.stream().map((EgCC_finale) -> EgCC_finale).reduce(deposito, (accumulator, _item) -> accumulator + _item);
            this.totalFlujosCC.add(0.0);
            this.totalFlujosCC.stream().forEach((totalFlujosCC1) -> {
                tablaIngresos2.addCell("$" + totalFlujosCC1);
            });
            tablaIngresos2.addCell("$" + deposito);
            documento.add(tablaIngresos2);

            //<----Bloque 3
            PdfPTable tablaIngresosT = new PdfPTable(7);
            tablaIngresosT.addCell("Total Ingresos");
            List<Double> Eg_Tot = new ArrayList<>();
            this.totalIngresos.stream().forEach((totalIngresos1) -> {
                Eg_Tot.add(totalIngresos1);
            });
            double total = 0;
            total = Eg_Tot.stream().map((Eg_Tots) -> Eg_Tots).reduce(total, (accumulator, _item) -> accumulator + _item);
            this.totalIngresos.add(0.0);
            this.totalIngresos.stream().forEach((totalIngreso) -> {
                tablaIngresosT.addCell("$" + totalIngreso);
            });
            Double difE = total;
            tablaIngresosT.addCell("$" + total);
            documento.add(tablaIngresosT);
            //******************************************************************************

            Paragraph gastos = new Paragraph("\n" + "Gastos" + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.RED) //Title
            );
            documento.add(gastos);

            //<----------Block Start
            PdfPTable tablaGastosV = new PdfPTable(7);
            tablaGastosV.addCell("Costos de Ventas");
            List<Double> gastosV = new ArrayList<>();
            this.totalGV.stream().forEach((totalGV1) -> {
                gastosV.add(totalGV1);
            });
            Double GVfinal = 0.0;
            GVfinal = gastosV.stream().map((gastosV1) -> gastosV1).reduce(GVfinal, (accumulator, _item) -> accumulator + _item);
            this.totalGV.add(0.0);
            this.totalGV.stream().forEach((totalGV1) -> {
                tablaGastosV.addCell("$" + totalGV1.floatValue());
            });
            tablaGastosV.addCell("$" + GVfinal.floatValue());
            documento.add(tablaGastosV);

            //<----------
            PdfPTable tablaGastosAOC = new PdfPTable(7);
            tablaGastosAOC.addCell("Gastos Fijos Operativos");
            List<Double> gastosAd = new ArrayList<>();
            this.totalGO.stream().forEach((totalGO1) -> {
                gastosAd.add(totalGO1);
            });
            Double GOfinal = 0.0;
            GOfinal = gastosAd.stream().map((gastosAd1) -> gastosAd1).reduce(GOfinal, (accumulator, _item) -> accumulator + _item);
            this.totalGO.add(0.0);
            this.totalGO.stream().forEach((totalGO1) -> {
                tablaGastosAOC.addCell("$" + totalGO1.floatValue());
            });
            tablaGastosAOC.addCell("$" + GOfinal.floatValue());
            documento.add(tablaGastosAOC);
            //<------

            PdfPTable TotalesGastos = new PdfPTable(7);
            TotalesGastos.addCell("Total Gastos");
            List<Double> listGastos = new ArrayList<>();
            this.totalGastos.stream().forEach((totalGasto) -> {
                listGastos.add(totalGasto);
            });
            Double totalG = 0.0;
            totalG = listGastos.stream().map((listGasto) -> listGasto).reduce(totalG, (accumulator, _item) -> accumulator + _item);
            this.totalGastos.add(0.0);
            this.totalGastos.stream().forEach((totalGasto) -> {
                TotalesGastos.addCell("$" + totalGasto);
            });
            Double difG = totalG;
            TotalesGastos.addCell("$" + totalG.floatValue());
            documento.add(TotalesGastos);

            //<--------BlockEnd
            //<-------Diferencias
            Paragraph Dif = new Paragraph("\n" + "Diferencia " + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(Dif);
            PdfPTable diferencias = new PdfPTable(7);
            diferencias.addCell("Total Utilidad");
            List<Double> brecha = new ArrayList<>();
            for (int i = 0; i < this.totalGastos.size(); i++) {
                brecha.add(totalIngresos.get(i) - this.totalGastos.get(i));
            }
            brecha.add(difE - difG);
            brecha.stream().forEach((brecha1) -> {
                diferencias.addCell("$" + brecha1.floatValue());
            });
            documento.add(diferencias);
            //----->

            Paragraph rentabilidad = new Paragraph("\n" + "Rentabilidad" + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.GREEN) //Title
            );
            documento.add(rentabilidad);

            PdfPTable margenes = new PdfPTable(7);
            margenes.addCell("Margen de Rentabilidad");
            List<Double> rent = new ArrayList<>();
            for (int i = 0; i < this.totalIngresos.size(); i++) {
                Double utilidad = brecha.get(i);
                Double ingreso = this.totalIngresos.get(i);
                rent.add((utilidad / ingreso) * 100);
            }
            Double u = brecha.get(brecha.size() - 1);
            rent.add((u / difE) * 100);
            for (int i = 0; i < rent.size(); i++) {
                margenes.addCell("" + Math.round(rent.get(i)) + "%");
            }
            documento.add(margenes);

            //Tabla Bancos
            Paragraph Bancos = new Paragraph("\n" + "Bancos" + "\n\n",
                    FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLUE) //Title
            );
            documento.add(Bancos);
            PdfPTable tablaBancos = new PdfPTable(7);
            tablaBancos.addCell("Banco");
            tablaBancos.addCell("Semana 1");
            tablaBancos.addCell("Semana 2");
            tablaBancos.addCell("Semana 3");
            tablaBancos.addCell("Semana 4");
            tablaBancos.addCell("Semana 5");
            tablaBancos.addCell(" Finales ");
            List<Double> Bfinales = new ArrayList<>();
            for (Double BSemana51 : this.BancosSemana5) {
                Bfinales.add(Double.parseDouble(String.valueOf(BSemana51)));
            }
            for (int i = 0; i < this.BancosSemana1.size(); i++) {
                tablaBancos.addCell(this.BancosSemana1.get(i).getRazon());
                tablaBancos.addCell("$" + String.valueOf(this.BancosSemana1.get(i).getMonto()));
                tablaBancos.addCell("$" + String.valueOf(this.BancosSemana2.get(i)));
                tablaBancos.addCell("$" + String.valueOf(this.BancosSemana3.get(i)));
                tablaBancos.addCell("$" + String.valueOf(this.BancosSemana4.get(i)));
                tablaBancos.addCell("$" + String.valueOf(this.BancosSemana5.get(i)));
                tablaBancos.addCell("$" + String.valueOf(Bfinales.get(i)));
            }
            documento.add(tablaBancos);
            PdfPTable BTotales = new PdfPTable(7);
            BTotales.addCell("Total Bancos");
            this.BancosTotal.stream().forEach((BancosTotal1) -> {
                BTotales.addCell("$" + String.valueOf(BancosTotal1));
            });
            BTotales.addCell("$" + String.valueOf(this.BancosTotal.get(this.BancosTotal.size() - 1)));
            documento.add(BTotales);
            //<-----
            documento.close();
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println(e);
        }

    }

    public void getReportData(String mes) {
        getCobros(mes);
        getPagos(mes);
        getBancos(mes);
        getFlujos(mes);
    }

    public void getCobros(String mes) {
        //Listas
        List<Registro> Pagossemana1 = new ArrayList<>();
        List<Double> PagosS2 = new ArrayList<>();
        List<Double> PagosS3 = new ArrayList<>();
        List<Double> PagosS4 = new ArrayList<>();           //<---------- Updated to double as methods
        List<Double> PagosS5 = new ArrayList<>();
        List<Double> totales = new ArrayList<>();
        //Variables
        Registro newRegistro;
        Double pago;
        Double total;
        //Query Montos
        String query = "select razon,monto from registro where mes = '" + mes + "' and tipo = 'Cobro' and numsemana = 1";
        String query2 = "select monto from registro where mes = '" + mes + "' and tipo = 'Cobro' and numsemana=2";
        String query3 = "select monto from registro where mes = '" + mes + "' and tipo = 'Cobro' and numsemana=3";
        String query4 = "select monto from registro where mes = '" + mes + "' and tipo = 'Cobro' and numsemana=4";
        String query5 = "select monto from registro where mes = '" + mes + "' and tipo = 'Cobro' and numsemana=5";
        //Query Totales
        String total1 = "select SUM(monto) as total from registro where numsemana = 1 and tipo ='Cobro' and mes ='" + mes + "';";
        String total2 = "select SUM(monto) as total from registro where numsemana = 2 and tipo ='Cobro' and mes ='" + mes + "';";
        String total3 = "select SUM(monto) as total from registro where numsemana = 3 and tipo ='Cobro' and mes ='" + mes + "';";
        String total4 = "select SUM(monto) as total from registro where numsemana = 4 and tipo ='Cobro' and mes ='" + mes + "';";
        String total5 = "select SUM(monto) as total from registro where numsemana = 5 and tipo ='Cobro' and mes ='" + mes + "';";

        try {

            //Semana 1 y Razon
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cashflow", "postgres", "7132");
            PreparedStatement req = conn.prepareStatement(query);
            ResultSet res = req.executeQuery();
            while (res.next()) {
                String razon = res.getString("razon");
                double monto = res.getDouble("monto");
                newRegistro = new Registro(razon, monto);
                Pagossemana1.add(newRegistro);
            }
            this.CobrosSemana1 = Pagossemana1;

            //Montos Semana 2
            req = conn.prepareStatement(query2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS2.add(pago);
            }
            this.CobrosSemana2 = PagosS2;

            //Montos Semana3
            req = conn.prepareStatement(query3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS3.add(pago);
            }
            this.CobrosSemana3 = PagosS3;

            //Montos Semana4
            req = conn.prepareStatement(query4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS4.add(pago);
            }
            this.CobrosSemana4 = PagosS4;

            //Montos Semana5
            req = conn.prepareStatement(query5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS5.add(pago);
            }
            this.CobrosSemana5 = PagosS5;

            req = conn.prepareStatement(total1);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total"); //<-Updated Method data type and columns
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }
            this.CobrosTotal = totales;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getPagos(String mes) {
        //Listas
        List<Registro> Pagossemana1 = new ArrayList<>();
        List<Double> PagosS2 = new ArrayList<>();
        List<Double> PagosS3 = new ArrayList<>();
        List<Double> PagosS4 = new ArrayList<>();
        List<Double> PagosS5 = new ArrayList<>();
        List<Double> totales = new ArrayList<>();
        //Variables
        Registro newRegistro;
        Double pago;
        Double total;
        //Query Montos
        String query = "select razon,monto from registro where mes = '" + mes + "' and tipo = 'Pago' and numsemana=1";
        String query2 = "select monto from registro where mes = '" + mes + "' and tipo = 'Pago' and numsemana=2";
        String query3 = "select monto from registro where mes = '" + mes + "' and tipo = 'Pago' and numsemana=3";
        String query4 = "select monto from registro where mes = '" + mes + "' and tipo = 'Pago' and numsemana=4";
        String query5 = "select monto from registro where mes = '" + mes + "' and tipo = 'Pago' and numsemana=5";
        //Query Totales
        String total1 = "select SUM(monto) as total from registro where numsemana = 1 and tipo ='Pago' and mes ='" + mes + "';";
        String total2 = "select SUM(monto) as total from registro where numsemana = 2 and tipo ='Pago' and mes ='" + mes + "';";
        String total3 = "select SUM(monto) as total from registro where numsemana = 3 and tipo ='Pago' and mes ='" + mes + "';";
        String total4 = "select SUM(monto) as total from registro where numsemana = 4 and tipo ='Pago' and mes ='" + mes + "';";
        String total5 = "select SUM(monto) as total from registro where numsemana = 5 and tipo ='Pago' and mes ='" + mes + "';";

        try {

            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cashflow", "postgres", "7132");
            PreparedStatement req = conn.prepareStatement(query);
            ResultSet res = req.executeQuery();
            while (res.next()) {
                String razon = res.getString("razon");
                double monto = res.getDouble("monto");
                newRegistro = new Registro(razon, monto);
                Pagossemana1.add(newRegistro);
            }
            this.PagosSemana1 = Pagossemana1;

            req = conn.prepareStatement(query2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS2.add(pago);
            }
            this.PagosSemana2 = PagosS2;

            req = conn.prepareStatement(query3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS3.add(pago);
            }
            this.PagosSemana3 = PagosS3;

            req = conn.prepareStatement(query4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS4.add(pago);
            }
            this.PagosSemana4 = PagosS4;

            req = conn.prepareStatement(query5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                PagosS5.add(pago);
            }
            this.PagosSemana5 = PagosS5;

            req = conn.prepareStatement(total1);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            this.PagosTotal = totales;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void getBancos(String mes) {

        List<Registro> Bancossemana1 = new ArrayList<>();
        List<Double> BancosS2 = new ArrayList<>();
        List<Double> BancosS3 = new ArrayList<>();
        List<Double> BancosS4 = new ArrayList<>();
        List<Double> BancoS5 = new ArrayList<>();
        List<Double> totales = new ArrayList<>();
        //Variables
        Registro newRegistro;
        Double pago;
        Double total;
        //Query Montos
        String query = "select razon,monto from registro where mes = '" + mes + "' and tipo = 'Banco' and numsemana=1";
        String query2 = "select monto from registro where mes = '" + mes + "' and tipo = 'Banco' and numsemana=2";
        String query3 = "select monto from registro where mes = '" + mes + "' and tipo = 'Banco' and numsemana=3";
        String query4 = "select monto from registro where mes = '" + mes + "' and tipo = 'Banco' and numsemana=4";
        String query5 = "select monto from registro where mes = '" + mes + "' and tipo = 'Banco' and numsemana=5";
        //Query Totales
        String total1 = "select SUM(monto) as total from registro where numsemana = 1 and tipo ='Banco' and mes ='" + mes + "';";
        String total2 = "select SUM(monto) as total from registro where numsemana = 2 and tipo ='Banco' and mes ='" + mes + "';";
        String total3 = "select SUM(monto) as total from registro where numsemana = 3 and tipo ='Banco' and mes ='" + mes + "';";
        String total4 = "select SUM(monto) as total from registro where numsemana = 4 and tipo ='Banco' and mes ='" + mes + "';";
        String total5 = "select SUM(monto) as total from registro where numsemana = 5 and tipo ='Banco' and mes ='" + mes + "';";

        try {

            PreparedStatement req = conn.prepareStatement(query);
            ResultSet res = req.executeQuery();
            while (res.next()) {
                String razon = res.getString("razon");
                double monto = res.getDouble("monto");
                newRegistro = new Registro(razon, monto);
                Bancossemana1.add(newRegistro);
            }
            this.BancosSemana1 = Bancossemana1;

            req = conn.prepareStatement(query2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                BancosS2.add(pago);
            }
            this.BancosSemana2 = BancosS2;

            req = conn.prepareStatement(query3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                BancosS3.add(pago);
            }
            this.BancosSemana3 = BancosS3;

            req = conn.prepareStatement(query4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                BancosS4.add(pago);
            }
            this.BancosSemana4 = BancosS4;

            req = conn.prepareStatement(query5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("monto");
                pago = monto;
                BancoS5.add(pago);
            }
            this.BancosSemana5 = BancoS5;

            req = conn.prepareStatement(total1);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            req = conn.prepareStatement(total5);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(total);
            }

            this.BancosTotal = totales;
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void getFlujos(String mes) {
        //Listas Ingresos
        List<Double> totales = new ArrayList<>();
        List<Double> totalesCC = new ArrayList<>();
        List<Double> totalesSum = new ArrayList<>();
        //Listas Egresos
        List<Double> Etotales = new ArrayList<>();
        List<Double> EtotalesAOC = new ArrayList<>();
        List<Double> EtotalesSum = new ArrayList<>();
        String month = getMes(mes);
        //Querys Ingresos
        String Query = "select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like '%Efectivo';";
        String Query2 = "select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like '%Efectivo';";
        String Query3 = "select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like '%Efectivo';";
        String Query4 = "select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like '%Efectivo';";

        String queryT = "select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like '%Deposito';";
        String queryT2 = "select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like '%Deposito';";
        String queryT3 = "select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like '%Deposito';";
        String queryT4 = "select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like '%Deposito';";

        String queryS = "select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like 'Venta%';";
        String queryS2 = "select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like 'Venta%';";
        String queryS3 = "select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like 'Venta%';";
        String queryS4 = "select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like 'Venta%';";
        //Querys Egresos
        String queryG = "Select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like '%Costo%';";
        String queryG2 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like '%Costo%';";
        String queryG3 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like '%Costo%';";
        String queryG4 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like '%Costo%';";

        String QG = "Select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like '%AOC%';";
        String QG2 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like '%AOC%';";
        String QG3 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like '%AOC%';";
        String QG4 = "Select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like '%AOC%';";

        String SE1 = "select SUM(monto) as total from flujo where fecha between '"+month+"-01' and '"+month+"-10' and categoria like '%sto%';";
        String SE2 = "select SUM(monto) as total from flujo where fecha between '"+month+"-11' and '"+month+"-17' and categoria like '%sto%';";
        String SE3 = "select SUM(monto) as total from flujo where fecha between '"+month+"-18' and '"+month+"-24' and categoria like '%sto%';";
        String SE4 = "select SUM(monto) as total from flujo where fecha between '"+month+"-25' and '"+month+"-31' and categoria like '%sto%';";

        Double total;

        try {

            PreparedStatement req = conn.prepareStatement(Query);
            ResultSet res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(monto);
            }

            req = conn.prepareStatement(Query2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(monto);
            }

            req = conn.prepareStatement(Query3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(monto);
            }

            req = conn.prepareStatement(Query4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totales.add(monto);
            }

            this.totalFlujosEf = totales;

            req = conn.prepareStatement(queryT);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesCC.add(monto);
            }

            req = conn.prepareStatement(queryT2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesCC.add(monto);
            }

            req = conn.prepareStatement(queryT3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesCC.add(monto);
            }

            req = conn.prepareStatement(queryT4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesCC.add(monto);
            }

            this.totalFlujosCC = totalesCC;

            req = conn.prepareStatement(queryS);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesSum.add(monto);
            }

            req = conn.prepareStatement(queryS2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesSum.add(monto);
            }

            req = conn.prepareStatement(queryS3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesSum.add(monto);
            }

            req = conn.prepareStatement(queryS4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                totalesSum.add(monto);
            }
            this.totalIngresos = totalesSum;

            //<---------------Aqui
            req = conn.prepareStatement(queryG);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                Etotales.add(monto);
            }

            req = conn.prepareStatement(queryG2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                Etotales.add(monto);

            }

            req = conn.prepareStatement(queryG3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                Etotales.add(monto);
            }

            req = conn.prepareStatement(queryG4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                Etotales.add(monto);
            }

            this.totalGV = Etotales;

            //Aqui<-----------
            req = conn.prepareStatement(QG);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesAOC.add(monto);

            }

            req = conn.prepareStatement(QG2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesAOC.add(monto);
            }

            req = conn.prepareStatement(QG3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesAOC.add(monto);

            }

            req = conn.prepareStatement(QG4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesAOC.add(monto);

            }

            //RS
            req = conn.prepareStatement(SE1);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesSum.add(monto);
            }

            req = conn.prepareStatement(SE2);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesSum.add(monto);

            }

            req = conn.prepareStatement(SE3);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesSum.add(monto);

            }

            req = conn.prepareStatement(SE4);
            res = req.executeQuery();
            while (res.next()) {
                double monto = res.getDouble("total");
                total = monto;
                EtotalesSum.add(monto);
            }

            this.totalGO = EtotalesAOC;
            this.totalGastos = EtotalesSum;
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getMes(String mes) {
        
        String date = "Noviembre";
        switch (mes) {
            case "Noviembre":
                date="2019-11";
                break;
            case "Diciembre":
                date="2019-12";
                break;
            case "Enero":
                date="2021-01";
                break;
            case "Febrero":
                date="2021-02";
                break;
            case "Marzo":
                date="2021-03";
                break;
            case "Abril":
                date="2021-04";
                break;
            case "Mayo":
                date="2021-05";
                break;
            case "Junio":
                date="2021-06";
                break;
            case "Julio":
                date="2021-07";
                break;
            case "Agosto":
                date="2021-08";
                break;
            case "Septiembre":
                date="2021-09";
                break;
            case "Octubre":
                date="2021-10";
                break;
            default:
                date="2019-11";
                break;
        }
        return date;
    }
}
