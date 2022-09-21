using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_materialFinishAndWasteCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "finishAmount",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "finishQuantity",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "finishUnitPrice",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "wasteAmount",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "wasteQuantity",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "wasteUnitPrice",
                table: "p_material",
                type: "decimal(18,6)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "finishAmount",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "finishQuantity",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "finishUnitPrice",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "wasteAmount",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "wasteQuantity",
                table: "p_material");

            migrationBuilder.DropColumn(
                name: "wasteUnitPrice",
                table: "p_material");
        }
    }
}
