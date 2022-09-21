using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterCheckPaymentCheckCntToRdCnt : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "checkCnt",
                table: "checkPayment",
                newName: "rdCnt");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdCnt",
                table: "checkPayment",
                newName: "checkCnt");
        }
    }
}
