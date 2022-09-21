using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class company : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "company",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyName = table.Column<string>(maxLength: 50, nullable: false),
                    companyAddress = table.Column<string>(maxLength: 100, nullable: false),
                    addressCode = table.Column<string>(maxLength: 10, nullable: false),
                    taxpayerId = table.Column<string>(maxLength: 30, nullable: false),
                    creditCode = table.Column<string>(maxLength: 30, nullable: false),
                    invoiceTitle = table.Column<string>(maxLength: 50, nullable: false),
                    industryCode = table.Column<string>(maxLength: 20, nullable: false),
                    mainIndustry = table.Column<string>(maxLength: 10, nullable: false),
                    linkTel = table.Column<string>(maxLength: 20, nullable: false),
                    domain = table.Column<string>(nullable: true),
                    email = table.Column<string>(nullable: true),
                    owner = table.Column<string>(maxLength: 30, nullable: false),
                    capital = table.Column<int>(nullable: false),
                    members = table.Column<int>(nullable: false),
                    depts = table.Column<int>(nullable: false),
                    registerTime = table.Column<DateTime>(nullable: false),
                    firstDevFee = table.Column<DateTime>(nullable: true),
                    accountSystem = table.Column<string>(maxLength: 20, nullable: true),
                    taxAuthorities = table.Column<string>(maxLength: 30, nullable: true),
                    realTaxAuthorities = table.Column<string>(maxLength: 30, nullable: true),
                    hasDevAccount = table.Column<bool>(nullable: false),
                    highTec = table.Column<bool>(nullable: false),
                    highTecIndustry = table.Column<string>(maxLength: 20, nullable: false),
                    businessLicense = table.Column<string>(maxLength: 80, nullable: false),
                    logo = table.Column<string>(maxLength: 80, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_company", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "company");
        }
    }
}
