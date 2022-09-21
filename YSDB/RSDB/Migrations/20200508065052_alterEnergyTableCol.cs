using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterEnergyTableCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "amount",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "quantity",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "used",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "usedRatio",
                table: "p_energy");

            migrationBuilder.RenameColumn(
                name: "remainRatio",
                table: "d_energy",
                newName: "remainAmount");

            migrationBuilder.AddColumn<decimal>(
                name: "rdHour",
                table: "p_energy",
                type: "decimal(6,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "totalHour",
                table: "p_energy",
                type: "decimal(6,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "trialType",
                table: "p_energy",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdHour",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "totalHour",
                table: "p_energy");

            migrationBuilder.DropColumn(
                name: "trialType",
                table: "p_energy");

            migrationBuilder.RenameColumn(
                name: "remainAmount",
                table: "d_energy",
                newName: "remainRatio");

            migrationBuilder.AddColumn<decimal>(
                name: "amount",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "quantity",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "used",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "usedRatio",
                table: "p_energy",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
