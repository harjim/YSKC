using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class accountTitleId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_salary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_inspection",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_energy",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_design",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "accountTitleId",
                table: "d_bonus",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "fullAccountName",
                table: "accountTitle",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "accountTitleId",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "fullAccountName",
                table: "accountTitle");
        }
    }
}
