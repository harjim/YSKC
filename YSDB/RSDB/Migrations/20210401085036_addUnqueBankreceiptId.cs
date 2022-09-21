using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addUnqueBankreceiptId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_t_investment_bankReceipt_investmentId_bankReceiptId",
                table: "t_investment_bankReceipt",
                columns: new[] { "investmentId", "bankReceiptId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_t_investment_bankReceipt_investmentId_bankReceiptId",
                table: "t_investment_bankReceipt");
        }
    }
}
