using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class expertcustomer : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "customer",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    companyName = table.Column<string>(maxLength: 50, nullable: false),
                    companyAddress = table.Column<string>(maxLength: 100, nullable: false),
                    addressCode = table.Column<string>(maxLength: 10, nullable: false),
                    taxpayerId = table.Column<string>(maxLength: 30, nullable: false),
                    creditCode = table.Column<string>(maxLength: 30, nullable: false),
                    invoiceTitle = table.Column<string>(maxLength: 50, nullable: false),
                    industryCode = table.Column<string>(maxLength: 20, nullable: false),
                    mainIndustry = table.Column<string>(maxLength: 10, nullable: false),
                    linkMan = table.Column<string>(maxLength: 20, nullable: false),
                    linkTel = table.Column<string>(maxLength: 20, nullable: false),
                    email = table.Column<string>(maxLength: 100, nullable: true),
                    owner = table.Column<string>(maxLength: 30, nullable: false),
                    capital = table.Column<int>(nullable: false),
                    registerTime = table.Column<DateTime>(nullable: false),
                    firstDevFee = table.Column<DateTime>(nullable: true),
                    accountSystem = table.Column<string>(maxLength: 20, nullable: true),
                    taxAuthorities = table.Column<string>(maxLength: 30, nullable: true),
                    realTaxAuthorities = table.Column<string>(maxLength: 30, nullable: true),
                    hasDevAccount = table.Column<bool>(nullable: false),
                    highTec = table.Column<bool>(nullable: false),
                    highTecIndustry = table.Column<string>(maxLength: 20, nullable: true),
                    from = table.Column<byte>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    creatorTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_customer", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "expert",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    expertNumber = table.Column<string>(maxLength: 30, nullable: false),
                    realName = table.Column<string>(maxLength: 15, nullable: false),
                    photoUrl = table.Column<string>(maxLength: 100, nullable: false),
                    birthday = table.Column<DateTime>(nullable: false),
                    level = table.Column<int>(nullable: false),
                    validDate = table.Column<DateTime>(nullable: true),
                    issueDate = table.Column<DateTime>(nullable: true),
                    idNumber = table.Column<string>(maxLength: 30, nullable: false),
                    gender = table.Column<byte>(nullable: false),
                    industryCode = table.Column<string>(maxLength: 20, nullable: false),
                    phone = table.Column<string>(maxLength: 30, nullable: false),
                    email = table.Column<string>(maxLength: 30, nullable: true),
                    policitalStatus = table.Column<byte>(nullable: false),
                    eduLevel = table.Column<int>(nullable: false),
                    address = table.Column<string>(maxLength: 30, nullable: false),
                    postcode = table.Column<string>(nullable: true),
                    workHistory = table.Column<string>(maxLength: 400, nullable: false),
                    honour = table.Column<string>(maxLength: 300, nullable: true),
                    status = table.Column<byte>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    uuid = table.Column<string>(maxLength: 50, nullable: false),
                    qrcode = table.Column<string>(maxLength: 100, nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_expert", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "customer");

            migrationBuilder.DropTable(
                name: "expert");
        }
    }
}
