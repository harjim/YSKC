using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class insuranceconfig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "sys_insuranceConfig",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    startMonth = table.Column<DateTime>(nullable: false),
                    endMonth = table.Column<DateTime>(nullable: true),
                    endowment = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    medical = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    unemployment = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    injury = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    maternity = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    house = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    endowmentOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    medicalOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    unemploymentOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    injuryOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    maternityOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false),
                    houseOfCom = table.Column<decimal>(type: "decimal(6,4)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_insuranceConfig", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "sys_insuranceConfig");
        }
    }
}
