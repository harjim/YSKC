using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_energy_amount : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "remainQuantiy",
                table: "d_energy",
                newName: "remainRatio");

            migrationBuilder.AddColumn<decimal>(
                name: "amount",
                table: "p_energy",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "quantity",
                table: "p_energy",
                type: "decimal(10,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "usedRatio",
                table: "p_energy",
                type: "decimal(12,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "amount",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "quantity",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "usedRatio",
                table: "p_energy");

            migrationBuilder.RenameColumn(
                name: "remainRatio",
                table: "d_energy",
                newName: "remainQuantiy");
        }
    }
}
