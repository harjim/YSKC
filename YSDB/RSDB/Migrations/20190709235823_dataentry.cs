using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dataentry : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "d_attendance",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    ename = table.Column<string>(maxLength: 20, nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    attData = table.Column<string>(maxLength: 64, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_attendance", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_design",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    dname = table.Column<string>(maxLength: 20, nullable: false),
                    designDate = table.Column<DateTime>(nullable: false),
                    dFee = table.Column<decimal>(type: "decimal(10,2)", nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_design", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_energy",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    ename = table.Column<string>(nullable: true),
                    type = table.Column<byte>(nullable: false),
                    occDate = table.Column<DateTime>(nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(10,2)", nullable: false),
                    unit = table.Column<string>(maxLength: 10, nullable: true),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_energy", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_equipment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    ecode = table.Column<string>(maxLength: 20, nullable: false),
                    ename = table.Column<string>(maxLength: 30, nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    equData = table.Column<string>(maxLength: 64, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_equipment", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_inspection",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    accDate = table.Column<DateTime>(nullable: false),
                    accNumber = table.Column<string>(maxLength: 20, nullable: false),
                    expense = table.Column<decimal>(nullable: false),
                    summary = table.Column<string>(maxLength: 120, nullable: false),
                    type = table.Column<byte>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_inspection", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_material",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    mcode = table.Column<string>(nullable: true),
                    mname = table.Column<string>(nullable: true),
                    acqDate = table.Column<DateTime>(nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(10,2)", nullable: false),
                    unit = table.Column<string>(maxLength: 10, nullable: true),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_material", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "d_salary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    ename = table.Column<string>(maxLength: 20, nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    workDays = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    pay = table.Column<decimal>(type: "decimal(10,2)", nullable: false),
                    insuranceFund = table.Column<decimal>(type: "decimal(10,2)", nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_d_salary", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "d_attendance");

            migrationBuilder.DropTable(
                name: "d_design");

            migrationBuilder.DropTable(
                name: "d_energy");

            migrationBuilder.DropTable(
                name: "d_equipment");

            migrationBuilder.DropTable(
                name: "d_inspection");

            migrationBuilder.DropTable(
                name: "d_material");

            migrationBuilder.DropTable(
                name: "d_salary");
        }
    }
}
