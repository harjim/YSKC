using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addInspectionAmountAndRemainCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdHour",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "totalHour",
                table: "p_design");

            migrationBuilder.AddColumn<decimal>(
                name: "rdAmount",
                table: "p_inspection",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdYield",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "totalYield",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "remainExpense",
                table: "d_inspection",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdAmount",
                table: "p_inspection");

            migrationBuilder.DropColumn(
                name: "rdYield",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "totalYield",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "remainExpense",
                table: "d_inspection");

            migrationBuilder.AddColumn<decimal>(
                name: "rdHour",
                table: "p_design",
                type: "decimal(6,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "totalHour",
                table: "p_design",
                type: "decimal(6,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
