using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class imember : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "endowmentOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "houseOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "injuryOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "maternityOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "medicalOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdEndowmentOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdHouseOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdInjuryOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdMaternityOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdMedicalOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "rdUnemploymentOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "unemploymentOfCom",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0m);


            migrationBuilder.AddColumn<decimal>(
                name: "endowment",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "endowmentOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "house",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "houseOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "injury",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "injuryOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "maternity",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "maternityOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "medical",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "medicalOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "unemployment",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "unemploymentOfCom",
                table: "d_salary",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.CreateTable(
                name: "i_equipment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    ecode = table.Column<string>(maxLength: 20, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_i_equipment", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "i_member",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_i_member", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "i_equipment");

            migrationBuilder.DropTable(
                name: "i_member");

            migrationBuilder.DropColumn(
                name: "endowmentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "houseOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "injuryOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "maternityOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "medicalOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdEndowmentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdHouseOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdInjuryOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdMaternityOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdMedicalOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "rdUnemploymentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "unemploymentOfCom",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "endowment",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "endowmentOfCom",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "house",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "houseOfCom",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "injury",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "injuryOfCom",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "maternity",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "maternityOfCom",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "medical",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "medicalOfCom",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "unemployment",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "unemploymentOfCom",
                table: "d_salary");
        }
    }
}
