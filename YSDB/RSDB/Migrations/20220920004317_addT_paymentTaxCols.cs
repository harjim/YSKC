using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_paymentTaxCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "taxRate",
                table: "t_contract_detail");

            migrationBuilder.AddColumn<decimal>(
                name: "amountTax",
                table: "t_payment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "taxRate",
                table: "t_payment",
                type: "decimal(5,4)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "amountTax",
                table: "t_payment");

            migrationBuilder.DropColumn(
                name: "taxRate",
                table: "t_payment");

            migrationBuilder.AddColumn<decimal>(
                name: "taxRate",
                table: "t_contract_detail",
                type: "decimal(5,4)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
