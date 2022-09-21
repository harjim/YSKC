using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class rddept : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "etype",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "equipment",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "rdDeptId",
                table: "equipment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "employee",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "rdDeptId",
                table: "employee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "auditor",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "billNo",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "biller",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "booker",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "deptId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "rdDeptId",
                table: "d_material",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "specification",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "warehouse",
                table: "d_material",
                maxLength: 30,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "rdDept",
                columns: table => new
                {
                    id = table.Column<short>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    deptName = table.Column<string>(nullable: true),
                    parentId = table.Column<short>(nullable: false),
                    level = table.Column<int>(nullable: false),
                    identity = table.Column<string>(maxLength: 80, nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_rdDept", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "rdDept");

            migrationBuilder.DropColumn(
                name: "etype",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "rdDeptId",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "rdDeptId",
                table: "employee");

            migrationBuilder.DropColumn(
                name: "auditor",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "billNo",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "biller",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "booker",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "deptId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "rdDeptId",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "specification",
                table: "d_material");

            migrationBuilder.DropColumn(
                name: "warehouse",
                table: "d_material");
        }
    }
}
