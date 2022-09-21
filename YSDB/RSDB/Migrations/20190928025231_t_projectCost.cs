using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class t_projectCost : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "t_projectCost",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    ctype = table.Column<int>(nullable: false),
                    cname = table.Column<string>(maxLength: 200, nullable: false),
                    model = table.Column<string>(maxLength: 30, nullable: false),
                    fillAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    payDates = table.Column<string>(maxLength: 200, nullable: false),
                    payee = table.Column<string>(maxLength: 50, nullable: false),
                    invoiceVoucher = table.Column<string>(maxLength: 200, nullable: false),
                    invoiceNumber = table.Column<string>(maxLength: 200, nullable: false),
                    invoiceDate = table.Column<DateTime>(nullable: false),
                    isBankTransfer = table.Column<int>(nullable: false),
                    bankVoucher = table.Column<string>(maxLength: 200, nullable: false),
                    bankTransferDates = table.Column<string>(maxLength: 200, nullable: false),
                    contractNumber = table.Column<string>(maxLength: 40, nullable: false),
                    contractDate = table.Column<DateTime>(nullable: false),
                    auditAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    quantity = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_projectCost", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_projectCost");
        }
    }
}
