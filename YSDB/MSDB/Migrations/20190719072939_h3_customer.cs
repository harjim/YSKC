using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class h3_customer : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "h3_customer",
                columns: table => new
                {
                    objectid = table.Column<string>(nullable: false),
                    name = table.Column<string>(nullable: true),
                    createdBy = table.Column<string>(nullable: true),
                    createdTime = table.Column<string>(nullable: true),
                    modifiedBy = table.Column<string>(nullable: true),
                    modifiedTime = table.Column<string>(nullable: true),
                    workflowInstanceId = table.Column<string>(nullable: true),
                    status = table.Column<int>(nullable: false),
                    clientName = table.Column<string>(nullable: true),
                    strategicLevel = table.Column<string>(nullable: true),
                    clientLevel = table.Column<string>(nullable: true),
                    salesRegion = table.Column<string>(nullable: true),
                    contactName = table.Column<string>(nullable: true),
                    mobile = table.Column<string>(nullable: true),
                    f0000003 = table.Column<string>(nullable: true),
                    f0000027 = table.Column<string>(nullable: true),
                    addr = table.Column<string>(nullable: true),
                    salesOpportunitie = table.Column<string>(nullable: true),
                    f0000022 = table.Column<string>(nullable: true),
                    ownerId = table.Column<string>(nullable: true),
                    ownerDeptId = table.Column<string>(nullable: true),
                    origin = table.Column<string>(nullable: true),
                    bank = table.Column<string>(nullable: true),
                    accountNumber = table.Column<string>(nullable: true),
                    address = table.Column<string>(nullable: true),
                    taxID = table.Column<string>(nullable: true),
                    clientFPName = table.Column<string>(nullable: true),
                    f0000038 = table.Column<string>(nullable: true),
                    salesAssistant = table.Column<string>(nullable: true),
                    f0000039 = table.Column<string>(nullable: true),
                    f0000029 = table.Column<string>(nullable: true),
                    f0000033 = table.Column<string>(nullable: true),
                    f0000031 = table.Column<string>(nullable: true),
                    f0000034 = table.Column<string>(nullable: true),
                    f0000036 = table.Column<string>(nullable: true),
                    f0000037 = table.Column<string>(nullable: true),
                    synDataDateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_h3_customer", x => x.objectid);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "h3_customer");
        }
    }
}
