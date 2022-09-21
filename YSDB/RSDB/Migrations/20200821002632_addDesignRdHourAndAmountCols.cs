using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addDesignRdHourAndAmountCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "rdAmount",
                table: "p_design",
                type: "decimal(6,2)",
                nullable: false,
                defaultValue: 0m);

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

            migrationBuilder.AddColumn<decimal>(
                name: "remainDFee",
                table: "d_design",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdAmount",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "rdHour",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "totalHour",
                table: "p_design");

            migrationBuilder.DropColumn(
                name: "remainDFee",
                table: "d_design");
        }
    }
}
