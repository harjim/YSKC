using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class BeianAddCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "constructionCost",
                table: "t_beian",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "equipmentCost",
                table: "t_beian",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "initWorkCapitalCost",
                table: "t_beian",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_t_investment_invoice_invoiceDetailId",
                table: "t_investment_invoice",
                column: "invoiceDetailId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_t_investment_contract_contactDetailId",
                table: "t_investment_contract",
                column: "contactDetailId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_t_investment_bankReceipt_bankReceiptId",
                table: "t_investment_bankReceipt",
                column: "bankReceiptId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_t_investment_invoice_invoiceDetailId",
                table: "t_investment_invoice");

            migrationBuilder.DropIndex(
                name: "IX_t_investment_contract_contactDetailId",
                table: "t_investment_contract");

            migrationBuilder.DropIndex(
                name: "IX_t_investment_bankReceipt_bankReceiptId",
                table: "t_investment_bankReceipt");

            migrationBuilder.DropColumn(
                name: "constructionCost",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "equipmentCost",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "initWorkCapitalCost",
                table: "t_beian");
        }
    }
}
