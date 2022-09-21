using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addDeptNameStrCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "contractTraceability");

            migrationBuilder.AlterColumn<string>(
                name: "deptIds",
                table: "p_subproject",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "p_subproject",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "p_project",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<string>(
                name: "deptIds",
                table: "p_project",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "p_project",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "p_project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "processSection",
                table: "p_project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "productLine",
                table: "p_project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "workshop",
                table: "p_project",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "equipment",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "processSection",
                table: "equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "productLine",
                table: "equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "workshop",
                table: "equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "employee",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "employee",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_salary",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_material",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_material",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_material",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_inspection",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_inspection",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_inspection",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_energy",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_energy",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_energy",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_design",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_design",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_design",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "d_bonus",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "c_attendance",
                maxLength: 100,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "deptName",
                table: "p_subproject");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "processSection",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "productLine",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "workshop",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "processSection",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "productLine",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "workshop",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_salary");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_inspection");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_equipment");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_energy");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_design");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "d_bonus");

            migrationBuilder.DropColumn(
                name: "deptName",
                table: "c_attendance");

            migrationBuilder.AlterColumn<string>(
                name: "deptIds",
                table: "p_subproject",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "p_project",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "deptIds",
                table: "p_project",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "p_project",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "equipment",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "employee",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_material",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_material",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_inspection",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_inspection",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_energy",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_energy",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "rdDeptId",
                table: "d_design",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "deptId",
                table: "d_design",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "contractTraceability",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    contractNumber = table.Column<string>(maxLength: 30, nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    customerName = table.Column<string>(maxLength: 200, nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    qrcode = table.Column<string>(maxLength: 100, nullable: false),
                    thecover = table.Column<string>(maxLength: 200, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_contractTraceability", x => x.id);
                });
        }
    }
}
