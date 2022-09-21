using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class insuranceConfig_add : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "endowmentOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "houseOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "injuryOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "maternityOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "medicalOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "unemploymentOfCom",
                table: "insuranceConfig",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endowmentOfCom",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "houseOfCom",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "injuryOfCom",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "maternityOfCom",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "medicalOfCom",
                table: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "unemploymentOfCom",
                table: "insuranceConfig");
        }
    }
}
