using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dee : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "dept",
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
                    fullPath = table.Column<string>(maxLength: 300, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_dept", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "employee",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    ename = table.Column<string>(maxLength: 20, nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    position = table.Column<string>(maxLength: 20, nullable: false),
                    idNumber = table.Column<string>(maxLength: 20, nullable: true),
                    edate = table.Column<DateTime>(nullable: false),
                    eduLevel = table.Column<int>(nullable: false),
                    etype = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_employee", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "equipment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    ename = table.Column<string>(maxLength: 30, nullable: false),
                    ecode = table.Column<string>(maxLength: 20, nullable: false),
                    emodal = table.Column<string>(maxLength: 20, nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(12,2)", nullable: false),
                    unit = table.Column<string>(maxLength: 10, nullable: false),
                    quantity = table.Column<int>(nullable: false),
                    usefullife = table.Column<int>(nullable: false),
                    purchaseDate = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_equipment", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "dept");

            migrationBuilder.DropTable(
                name: "employee");

            migrationBuilder.DropTable(
                name: "equipment");
        }
    }
}
